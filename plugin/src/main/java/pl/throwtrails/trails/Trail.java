package pl.throwtrails.trails;

import lombok.Getter;
import org.bukkit.entity.Projectile;

import java.util.List;

@Getter
public class Trail {

    private final boolean loop;
    private final String permission;
    private final int delay;
    private final int interval;
    private final List<String> notApplies;
    private final List<TrailParticle> lifeCycle;

    public Trail(boolean loop, String permission, int delay, int interval, List<String> notApplies, List<TrailParticle> lifeCycle) {
        this.loop = loop;
        this.permission = permission;
        this.delay = delay;
        this.interval = interval;
        this.notApplies = notApplies;
        this.lifeCycle = lifeCycle;
    }

}
