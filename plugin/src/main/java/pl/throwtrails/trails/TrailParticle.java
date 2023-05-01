package pl.throwtrails.trails;

import lombok.Getter;
import org.bukkit.Particle;

@Getter
public class TrailParticle {

    private final Particle particle;

    public TrailParticle(Particle particle) {
        this.particle = particle;
    }

}
