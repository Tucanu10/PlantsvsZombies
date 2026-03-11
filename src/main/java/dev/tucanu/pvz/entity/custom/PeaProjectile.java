package dev.tucanu.pvz.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class PeaProjectile extends Snowball {
    public PeaProjectile(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();
        Entity shooter = this.getOwner();

        // 1. Check if we should PHASE THROUGH (ignore the hit)
        if (shouldPhaseThrough(target, shooter)) {
            return; // By returning here, we never call discard() or damage logic
        }

        // 2. Normal Damage Logic
        float damage = 10.0f;
        LivingEntity ownerSource = shooter instanceof LivingEntity ? (LivingEntity) shooter : null;

        // Attempt to hurt the target
        if (target.hurt(this.damageSources().thrown(this, ownerSource), damage)) {
            this.discard();
        } else {
            // If the target is something else (like a boat or non-living thing)
            // that didn't take damage, we usually discard so peas don't fly forever.
            this.discard();
        }
    }

    /**
     * Logic to determine if a pea should pass through a target like a ghost.
     */
    private boolean shouldPhaseThrough(Entity target, Entity shooter) {
        // Phase through all Plants
        if (target instanceof PlantEntity) {
            return true;
        }

        // Phase through the Owner of the plant that shot this
        if (shooter instanceof PlantEntity plant) {
            if (target.getUUID().equals(plant.getOwnerUUID())) {
                return true;
            }
        }

        // Add logic here if you want them to phase through other players/teammates
        // if (target instanceof Player && someTeamCheck) return true;

        return false;
    }
}