package dev.tucanu.pvz.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class PeaProjectile extends Snowball
{
    public PeaProjectile(Level type, PeaShooterEntity level)
    {
        super(type, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult)
    {
        Entity target = entityHitResult.getEntity();

        if (target instanceof PlantEntity) { return; }

        float damage = 10.0f;

        LivingEntity owner = this.getOwner() instanceof LivingEntity ? (LivingEntity) this.getOwner() : null;
        target.hurt(this.damageSources().thrown(this, owner), damage);
        this.discard();
    }
}