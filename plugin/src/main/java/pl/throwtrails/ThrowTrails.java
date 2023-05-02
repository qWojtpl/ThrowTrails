package pl.throwtrails;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.throwtrails.commands.Commands;
import pl.throwtrails.commands.GUIHandler;
import pl.throwtrails.data.DataHandler;
import pl.throwtrails.events.Events;
import pl.throwtrails.trails.TrailsManager;

@Getter
public final class ThrowTrails extends JavaPlugin {

    private static ThrowTrails main;
    private TrailsManager trailsManager;
    private DataHandler dataHandler;
    private GUIHandler GUIHandler;
    private Events events;

    @Override
    public void onEnable() {
        main = this;
        this.trailsManager = new TrailsManager();
        this.dataHandler = new DataHandler();
        this.GUIHandler = new GUIHandler();
        dataHandler.loadConfig();
        this.events = new Events();
        getServer().getPluginManager().registerEvents(events, this);
        getCommand("throwtrails").setExecutor(new Commands());
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        getDataHandler().save();
        getLogger().info("Disabled.");
    }

    public static ThrowTrails getInstance() {
        return main;
    }
}
