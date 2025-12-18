package dev.tucanu.pvz.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PotatoMineEntity extends PlantEntity {
    // Synced data to tell the Client when to stop the wobble animation
    private static final EntityDataAccessor<Boolean> DATA_IS_PRIMED = SynchedEntityData.defineId(PotatoMineEntity.class, EntityDataSerializers.BOOLEAN);

    private boolean exploded = false;
    private int primingTimer = 60; // 60 ticks = 3 seconds

    public PotatoMineEntity(EntityType<? extends PlantEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PlantEntity.createAttributes();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        // Default is false (not primed, so it will wobble)
        builder.define(DATA_IS_PRIMED, false);
    }

    @Override
    public void tick() {
        super.tick(); // Run base logic first

        if (this.level().isClientSide()) return;

        // 1. Priming Logic
        if (primingTimer > 0) {
            primingTimer--;

            // When timer hits 0, update the data so the client knows we are ready
            if (primingTimer == 0) {
                this.entityData.set(DATA_IS_PRIMED, true);
                // Optional sound cue for priming completion
                this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 0.5F);
            }
            return; // Exit early: if it's still priming, it can't explode
        }

        // 2. Detection Logic (Only runs after timer hits 0)
        if (!this.exploded) {
            AABB searchBox = this.getBoundingBox().inflate(0.5D);
            List<Monster> nearby = this.level().getEntitiesOfClass(Monster.class, searchBox);

            if (!nearby.isEmpty()) {
                this.triggerExplosion();
            }
        }
    }

    /**
     * Helper method for the Model class to check animation state
     */
    public boolean isPrimed() {
        return this.entityData.get(DATA_IS_PRIMED);
    }

    private void triggerExplosion() {
        this.exploded = true;

        // 1. Protection Logic: Find nearby plants before the blast
        // We inflate the box to 3.0D to cover the 2.0F explosion radius
        List<PlantEntity> nearbyPlants = this.level().getEntitiesOfClass(PlantEntity.class, this.getBoundingBox().inflate(3.0D));

        for (PlantEntity plant : nearbyPlants) {
            if (plant == this) continue;

            // Mark as exploded so they don't trigger their own logic during the blast
            // and make them invulnerable so the explosion damage doesn't kill them
            plant.setInvulnerable(true);
        }

        // 2. Play Sound and Create Explosion
        this.level().playSound(null, this.blockPosition(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.level().explode(null, this.getX(), this.getY(), this.getZ(), 1.0F, Level.ExplosionInteraction.NONE);

        // 3. Cleanup: Restore invulnerability for survivors
        for (PlantEntity plant : nearbyPlants) {
            if (plant == this) continue;
            plant.setInvulnerable(false);
        }

        this.discard();
    }
}