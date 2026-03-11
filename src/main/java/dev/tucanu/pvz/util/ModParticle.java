package dev.tucanu.pvz.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class ModParticle extends TextureSheetParticle
{
    private final SpriteSet sprite;

    public ModParticle(ClientLevel level, double x, double y, double z,
                       double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.sprite = spriteSet;

        // Assign these to make the particle move!
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.gravity = (float)0.05;
        this.lifetime = 20;
        this.pickSprite(spriteSet);
    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
