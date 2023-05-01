package pl.throwtrails;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.throwtrails.commands.Commands;
import pl.throwtrails.data.DataHandler;
import pl.throwtrails.events.Events;
import pl.throwtrails.trails.TrailsManager;

@Getter
public final class ThrowTrails extends JavaPlugin {

    private static ThrowTrails main;
    private TrailsManager trailsManager;
    private DataHandler dataHandler;

    @Override
    public void onEnable() {
        main = this;
        this.trailsManager = new TrailsManager();
        this.dataHandler = new DataHandler();
        dataHandler.loadConfig();
        getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("throwtrails").setExecutor(new Commands());
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    public static ThrowTrails getInstance() {
        return main;
    }
}
