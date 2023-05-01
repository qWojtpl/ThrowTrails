package pl.throwtrails.data;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.trails.Trail;
import pl.throwtrails.trails.TrailParticle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private final ThrowTrails plugin = ThrowTrails.getInstance();

    public void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);
        ConfigurationSection section = yml.getConfigurationSection("trails");
        if(section == null) return;
        for(String key : section.getKeys(false)) {
            String path = "trails." + key + ".";
            boolean loop = yml.getBoolean(path + "loop");
            String permission = yml.getString(path + "permission");
            int delay = yml.getInt(path + "delay");
            int interval = yml.getInt(path + "interval");
            List<String> notApplies = yml.getStringList(path + "notApplies");
            List<String> lifeCycleStrings = yml.getStringList(path + "lifeCycle");
            List<TrailParticle> lifeCycle = new ArrayList<>();
            for(String s : lifeCycleStrings) {
                String[] split = s.split("/");
                Particle p;
                try {
                    p = Particle.valueOf(split[0]);
                } catch(IllegalArgumentException e) {
                    p = Particle.BUBBLE_POP;
                }
                lifeCycle.add(new TrailParticle(p));
            }
            Trail trail = new Trail(loop, permission, delay, interval, notApplies, lifeCycle);
            plugin.getTrailsManager().getTrails().put(key, trail);
        }
    }

}
