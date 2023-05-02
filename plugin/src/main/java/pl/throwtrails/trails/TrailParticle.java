package pl.throwtrails.trails;

import lombok.Getter;
import org.bukkit.Particle;

import java.util.List;

@Getter
public class TrailParticle {

    private final List<Particle> particles;

    public TrailParticle(List<Particle> particle) {
        this.particles = particle;
    }

}
