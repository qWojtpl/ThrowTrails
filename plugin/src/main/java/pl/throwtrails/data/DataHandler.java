package pl.throwtrails.data;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.trails.Trail;
import pl.throwtrails.trails.TrailParticle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class DataHandler {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final HashMap<String, String> preferences = new HashMap<>();

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
            String iconStr = yml.getString(path + "icon");
            Material m = Material.DIRT;
            if (iconStr != null) {
                m = Material.getMaterial(iconStr);
                if(m == null) {
                    m = Material.DIRT;
                }
            }
            ItemStack icon = new ItemStack(m);
            boolean loop = yml.getBoolean(path + "loop");
            String permission = yml.getString(path + "permission");
            int delay = yml.getInt(path + "delay");
            int interval = yml.getInt(path + "interval");
            List<String> notApplies = yml.getStringList(path + "notApplies");
            List<String> lifeCycleStrings = yml.getStringList(path + "lifeCycle");
            List<TrailParticle> lifeCycle = new ArrayList<>();
            for(String s : lifeCycleStrings) {
                String[] split = s.split("/");
                List<Particle> particles = new ArrayList<>();
                for(String value : split) {
                    Particle p;
                    try {
                        p = Particle.valueOf(value);
                    } catch (IllegalArgumentException e) {
                        p = Particle.BUBBLE_POP;
                    }
                    particles.add(p);
                }
                lifeCycle.add(new TrailParticle(particles));
            }
            Trail trail = new Trail(key, icon, loop, permission, delay, interval, notApplies, lifeCycle);
            plugin.getTrailsManager().getTrails().put(key, trail);
        }
        loadPreferences();
    }

    public void loadPreferences() {
        File dataFile = getDataFile();
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(dataFile);
        ConfigurationSection section = yml.getConfigurationSection("data");
        if(section == null) return;
        for(String player : section.getKeys(false)) {
            preferences.put(player, yml.getString("data." + player));
        }
    }

    public void setPreference(String player, String preference) {
        preferences.put(player, preference);
        File dataFile = getDataFile();
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(dataFile);
        yml.set("data." + player, preference);
        try {
            yml.save(dataFile);
        } catch(IOException e) {
            plugin.getLogger().severe("Cannot save data.yml!");
        }
    }

    public File getDataFile() {
        File dataFile = new File(plugin.getDataFolder(), "data.yml");
        if(!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
        return dataFile;
    }

    public void reload() {
        preferences.clear();
        plugin.getTrailsManager().getTrails().clear();
        loadConfig();
    }

}
