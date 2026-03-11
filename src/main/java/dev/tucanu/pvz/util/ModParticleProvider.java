package dev.tucanu.pvz.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ModParticleProvider implements ParticleProvider<SimpleParticleType>
{
    private final SpriteSet sprite;

    public ModParticleProvider(SpriteSet sprite)
    {
        this.sprite = sprite;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level,
                                             double x, double y, double z,
                                             double xSpeed, double ySpeed, double zSpeed) {
        // Pass the speeds here
        return new ModParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
    }
}
