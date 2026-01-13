package dev.tucanu.pvz.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PlantEntity extends Animal
{
    public AnimationState explodingAnimationState = new AnimationState();
    public AnimationState generatingSunAnimationState = new AnimationState();
    protected boolean exploded;

    public PlantEntity(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PlantEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 15.0D)
                .add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Monster.class, 15));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>
                (this, Monster.class, true, entity -> entity instanceof Enemy));
    }

    @Override
    public boolean isPushable()
    {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack)
    {
        return false;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.level().isClientSide && this.exploded)
        {
            this.explodingAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent)
    {
        return null;
    }
}
