package pl.throwtrails.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.data.DataHandler;
import pl.throwtrails.trails.Trail;

import java.util.HashMap;

@Getter
public class Events implements Listener {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final DataHandler dataHandler = plugin.getDataHandler();
    private final HashMap<Projectile, Integer> projectiles = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLaunch(ProjectileLaunchEvent event) {
        if(event.isCancelled()) return;
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getEntity().getShooter();
        Projectile projectile = event.getEntity();
        String preference = dataHandler.getPreferences().get(shooter.getName());
        if(preference == null) return;
        Trail trail = plugin.getTrailsManager().getByName(preference);
        if(trail == null) return;
        if(trail.getNotApplies().contains(event.getEntity().getType().name())) return;
        if(!shooter.hasPermission(trail.getPermission())) return;
        new ProjectileLaunch(projectile, trail);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        removeParticle(event.getEntity());
    }

    // <----> GUI <---->

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(plugin.getGUIHandler().isRegisteredInventory(event.getInventory())) {
            event.setCancelled(true);
            plugin.getGUIHandler().click((Player) event.getWhoClicked(), event.getSlot());
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if(plugin.getGUIHandler().isRegisteredInventory(event.getInventory())) event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        plugin.getGUIHandler().removeInventory(event.getInventory());
    }

    // <----> GUI <---->

    public void removeParticle(Projectile p) {
        if(p != null) {
            if(projectiles.containsKey(p)) {
                plugin.getServer().getScheduler().cancelTask(projectiles.get(p));
            }
        }
        projectiles.remove(p);
    }

}
