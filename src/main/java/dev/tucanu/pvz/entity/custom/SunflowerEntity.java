package dev.tucanu.pvz.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;


public class SunflowerEntity extends PlantEntity
{
    public SunflowerEntity(EntityType<? extends SunflowerEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PlantEntity.createAttributes();
    }

    // Inside SunflowerEntity.java
    private int sunGenerationTimer = 240;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            // Reset the animation state if it finished so it can be re-triggered
            // If it's a loop in the definition, you might need to stop it manually
        } else {
            // Server Logic
            sunGenerationTimer--;
            if (sunGenerationTimer <= 0) {
                // Tell the clients to play the animation
                this.level().broadcastEntityEvent(this, (byte) 10);
                sunGenerationTimer = 240;
                // Add your sun spawning logic here
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            // Stop it first to ensure it resets the animation timer to 0
            this.generatingSunAnimationState.stop();
            this.generatingSunAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }
}
