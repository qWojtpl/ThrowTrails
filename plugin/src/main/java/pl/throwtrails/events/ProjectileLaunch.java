package pl.throwtrails.events;

import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.entity.Projectile;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.trails.Trail;

import java.util.List;

@Getter
public class ProjectileLaunch {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final Projectile projectile;
    private final Trail trail;
    private int cycleCounter = 0;

    public ProjectileLaunch(Projectile projectile, Trail trail) {
        this.projectile = projectile;
        this.trail = trail;
        int task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(projectile.isDead()) plugin.getEvents().removeParticle(projectile);
            List<Particle> particles = trail.getLifeCycle().get(cycleCounter++).getParticles();
            for(Particle p : particles) {
                projectile.getWorld().spawnParticle(p, projectile.getLocation(), 10);
            }
            if(cycleCounter >= trail.getLifeCycle().size()) {
                if(trail.isLoop()) {
                    cycleCounter = 0;
                } else {
                    plugin.getEvents().removeParticle(projectile);
                }
            }
        }, trail.getDelay(), trail.getInterval());
        plugin.getEvents().getProjectiles().put(projectile, task);
    }

}
