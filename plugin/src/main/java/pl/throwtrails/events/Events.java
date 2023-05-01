package pl.throwtrails.events;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import pl.throwtrails.ThrowTrails;

import java.util.HashMap;

public class Events implements Listener {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final HashMap<Projectile, Integer> projectiles = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLaunch(ProjectileLaunchEvent event) {
        if(event.isCancelled()) return;
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        Projectile projectile = event.getEntity();
        int task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(projectile.isDead()) removeParticle(projectile);
            projectile.getWorld().spawnParticle(Particle.HEART, projectile.getLocation(), 10);
        }, 0L, 1L);
        projectiles.put(projectile, task);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        removeParticle(event.getEntity());
    }

    private void removeParticle(Projectile p) {
        if(p != null) {
            if(projectiles.containsKey(p)) {
                plugin.getServer().getScheduler().cancelTask(projectiles.get(p));
            }
        }
        projectiles.remove(p);
    }

}
