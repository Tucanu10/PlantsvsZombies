package dev.tucanu.pvz.entity.custom;

import dev.tucanu.pvz.util.ModItems;
import dev.tucanu.pvz.util.ModParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public class SunflowerEntity extends PlantEntity {

    // Define the constant here (20 ticks = 1 second)
    private static final int SUN_TICK_DELAY = 240;

    // This tracks the current countdown for this specific entity
    private int sunGenerationTimer = SUN_TICK_DELAY;

    public SunflowerEntity(EntityType<? extends SunflowerEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PlantEntity.createAttributes();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level() instanceof ServerLevel serverLevel) {
            // Server Logic: Count down
            if (sunGenerationTimer > 0) {
                sunGenerationTimer--;
            }

            if (sunGenerationTimer <= 0) {
                // Tell the clients to play the animation
                this.level().broadcastEntityEvent(this, (byte) 10);

                for (int i = 0; i < 10; i++) {
                    // a is between 0 and 0.5
                    double a = random.nextDouble() * 0.5;
                    double radius = Math.sqrt(a);

                    double phi = random.nextDouble() * 2.0 * Math.PI;
                    double theta = Math.acos(random.nextDouble());

                    // Directional vectors
                    double dx = radius * Math.sin(theta) * Math.cos(phi);
                    double dy = radius * Math.cos(theta); // z >= 0 constraint
                    double dz = radius * Math.sin(theta) * Math.sin(phi);

                    serverLevel.sendParticles(ModParticleTypes.SUN.get(),
                            this.getX(),
                            this.getY() + 0.5,
                            this.getZ(),
                            0,            // Count 0 uses the next 3 as velocity
                            dx, dy, dz,   // Speed is now controlled by your radius math
                            0.1           // Multiplier
                    );


                }
                // Spawn the sun item

                for(int i = 0; i < 25; i++)
                    this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(),
                            this.getY()+0.5, this.getZ(),
                            ModItems.SUN.get().getDefaultInstance()));

                // Reset the timer using the constant
                sunGenerationTimer = SUN_TICK_DELAY;
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.generatingSunAnimationState.stop();
            this.generatingSunAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }
}