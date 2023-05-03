package pl.throwtrails.trails;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Trail {

    private final String ID;
    private final Material icon;
    private final boolean loop;
    private final String permission;
    private final int delay;
    private final int interval;
    private final List<String> notApplies;
    private final List<TrailParticle> lifeCycle;

    public Trail(String ID, Material icon, boolean loop, String permission, int delay, int interval,
                 List<String> notApplies, List<TrailParticle> lifeCycle) {
        this.ID = ID;
        this.icon = icon;
        this.loop = loop;
        this.permission = permission;
        this.delay = delay;
        this.interval = interval;
        this.notApplies = notApplies;
        this.lifeCycle = lifeCycle;
    }

}
