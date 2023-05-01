package pl.throwtrails;

import org.bukkit.plugin.java.JavaPlugin;
import pl.throwtrails.events.Events;

public final class ThrowTrails extends JavaPlugin {

    private static ThrowTrails main;

    @Override
    public void onEnable() {
        main = this;
        getServer().getPluginManager().registerEvents(new Events(), this);
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
