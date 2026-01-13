package dev.tucanu.pvz.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CherryBombEntity extends PlantEntity {
    private int fuseTimer = 60; // 3 seconds

    public CherryBombEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();

        // Logic only runs on server
        if (this.level().isClientSide()) return;

        fuseTimer--;

        // Visual "hissing" or smoke effect before blowing up
        if (fuseTimer % 5 == 0 && fuseTimer > 0) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.5D, this.getZ(),
                    3, 0.2D, 0.2D, 0.2D, 0.05D);
        }

        if (fuseTimer <= 0) {
            this.explode();
        }
    }

    private void explode() {
        // 1. Visuals & Sound
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0F, 1.0F);

        if (this.level() instanceof ServerLevel serverLevel) {
            // Large explosion cloud
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            // Red "Cherry" flavored flame particles
            serverLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 0.5D, this.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.2D);
        }

        // 2. Damage Logic (3x3 Area)
        // inflate(1.5) on a 1x1 entity roughly covers a 3x3 block area
        AABB explosionArea = this.getBoundingBox().inflate(1.5D, 1.0D, 1.5D);
        List<Monster> targets = this.level().getEntitiesOfClass(Monster.class, explosionArea);

        for (Monster monster : targets) {
            monster.hurt(this.damageSources().explosion(this, this), 30.0F);
        }

        // 3. Remove the entity
        this.discard();
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }
}