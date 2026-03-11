package dev.tucanu.pvz.entity.custom;

import dev.tucanu.pvz.util.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PeaShooterEntity extends PlantEntity implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> STRAIGHT_MODE = SynchedEntityData.defineId(PeaShooterEntity.class, EntityDataSerializers.BOOLEAN);
    private int straightFireCooldown = 0;
    private float lockedYaw = 0;

    public PeaShooterEntity(EntityType<? extends PeaShooterEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PlantEntity.createAttributes();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STRAIGHT_MODE, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ModeAwareRangedAttackGoal(this, 1.0D, 30, 15.0F));
    }

    public void setOrientation(Direction facing) {
        this.lockedYaw = facing.toYRot();
        this.setYRot(lockedYaw);
        this.yHeadRot = lockedYaw;
        this.yBodyRot = lockedYaw;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isStraightMode()) {
            this.lockRotationToDirection();
            if (straightFireCooldown > 0) {
                straightFireCooldown--;
            } else {
                this.performStraightAttack();
                straightFireCooldown = 30;
            }
        }
    }

    private void lockRotationToDirection() {
        this.setYRot(lockedYaw);
        this.yHeadRot = lockedYaw;
        this.yBodyRot = lockedYaw;
    }

    private void performStraightAttack() {
        PeaProjectile pea = new PeaProjectile(this.level(), this);
        pea.setItem(new ItemStack(ModItems.PEA.get()));

        // Ensure the projectile knows this entity is the shooter
        pea.setOwner(this);

        Direction facing = Direction.fromYRot(lockedYaw);
        pea.shoot(facing.getStepX(), 0, facing.getStepZ(), 1.6F, 0.0F);

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 1.2F);
        this.level().addFreshEntity(pea);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (this.isStraightMode()) return;

        PeaProjectile pea = new PeaProjectile(this.level(), this);
        pea.setItem(new ItemStack(ModItems.PEA.get()));
        pea.setOwner(this);

        double d1 = target.getX() - this.getX();
        double d2 = target.getEyeY() - 1.1F - pea.getY();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;

        pea.shoot(d1, d2 + d4, d3, 1.6F, 5.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 1.2F);
        this.level().addFreshEntity(pea);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide) {
                boolean newMode = !isStraightMode();
                setStraightMode(newMode);
                player.displayClientMessage(Component.literal("Mode: " + (newMode ? "Straight" : "Targeted")), true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    public boolean isStraightMode() { return this.entityData.get(STRAIGHT_MODE); }
    public void setStraightMode(boolean isStraight) { this.entityData.set(STRAIGHT_MODE, isStraight); }

    static class ModeAwareRangedAttackGoal extends RangedAttackGoal {
        private final PeaShooterEntity peashooter;
        public ModeAwareRangedAttackGoal(PeaShooterEntity shooter, double speed, int interval, float radius) {
            super(shooter, speed, interval, radius);
            this.peashooter = shooter;
        }
        @Override public boolean canUse() { return super.canUse() && !peashooter.isStraightMode(); }
    }
}