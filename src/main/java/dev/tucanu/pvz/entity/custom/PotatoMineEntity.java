package dev.tucanu.pvz.entity.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PotatoMineEntity extends PlantEntity
{
    // flag to avoid double-triggering the explosion
    private boolean exploded = false;


    public PotatoMineEntity(EntityType<? extends PlantEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PlantEntity.createAttributes();
    }

    @Override
    public void tick()
    {
        super.tick();

        // Only run explosion-checking on the server
        if (!this.level().isClientSide() && !this.exploded)
        {
            // small detection radius around the entity
            AABB searchBox = this.getBoundingBox().inflate(0.75D);
            List<Monster> nearby = this.level().getEntitiesOfClass(Monster.class, searchBox);
            if (!nearby.isEmpty())
            {
                this.exploded = true;
                // Protect nearby potato mines from this explosion and prevent chain reactions
                List<PlantEntity> nearbyPlants = this.level().getEntitiesOfClass(PlantEntity.class, this.getBoundingBox().inflate(3.0D));
                for (PlantEntity plant : nearbyPlants)
                {
                    if (plant == this) continue;
                    // mark as exploded so they don't trigger; make temporarily invulnerable so the blast won't kill them
                    plant.exploded = true;
                    plant.setInvulnerable(true);
                }
                // play a short primed sound and create a non-destructive explosion that still damages entities
                this.level().playSound(null, this.blockPosition(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                // increase explosion power for more damage (3.0F)
                this.level().explode(null, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.NONE);
                // restore invulnerability state on nearby plants
                for (PlantEntity mine : nearbyPlants)
                {
                    if (mine == this) continue;
                    mine.setInvulnerable(false);
                    mine.exploded = false;
                }
                // remove the potato mine entity after exploding
                this.discard();
            }
        }
    }
}
