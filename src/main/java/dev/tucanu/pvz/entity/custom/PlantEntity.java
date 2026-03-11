package dev.tucanu.pvz.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
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

import java.util.Optional;
import java.util.UUID;

public class PlantEntity extends Animal implements OwnableEntity
{
    public AnimationState explodingAnimationState = new AnimationState();
    public AnimationState generatingSunAnimationState = new AnimationState();
    protected boolean exploded;
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(PlantEntity.class, EntityDataSerializers.OPTIONAL_UUID);

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
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class, true,
                (entity) -> entity instanceof Enemy && !entity.getUUID().equals(this.getOwnerUUID())
        ));
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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(OWNER_UUID, Optional.empty());
    }

    // Required by OwnableEntity interface
    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        UUID uuid = this.getOwnerUUID();
        return uuid == null ? null : this.level().getPlayerByUUID(uuid);
    }
}
