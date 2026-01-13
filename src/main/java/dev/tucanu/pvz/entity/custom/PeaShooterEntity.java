package dev.tucanu.pvz.entity.custom;

import dev.tucanu.pvz.ModItems;
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

public class PeaShooterEntity extends PlantEntity implements RangedAttackMob
{
    private static final EntityDataAccessor<Boolean> STRAIGHT_MODE = SynchedEntityData.defineId(PeaShooterEntity.class, EntityDataSerializers.BOOLEAN);

    // Cooldown timer for Straight Mode (matches the 30 ticks of the RangedAttackGoal)
    private int straightFireCooldown = 0;

    public PeaShooterEntity(EntityType<? extends PeaShooterEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PlantEntity.createAttributes();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STRAIGHT_MODE, true); // Default to Straight Mode
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        // Use a custom goal that checks the mode before running standard AI
        this.goalSelector.addGoal(1, new ModeAwareRangedAttackGoal(this, 1.0D, 30, 15.0F));
    }

    /**
     * Call this immediately after spawning to snap the entity to a cardinal direction.
     */
    public void setOrientation(Direction facing) {
        float yaw = facing.toYRot();
        this.setYRot(yaw);
        this.yHeadRot = yaw;
        this.yBodyRot = yaw;
        this.yHeadRotO = yaw;
        this.yBodyRotO = yaw;

        // If in straight mode, ensure we lock immediately
        if (this.isStraightMode()) {
            this.lockRotationToDirection();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.isStraightMode()) {
                // 1. Force Rotation to face the cardinal direction
                this.lockRotationToDirection();

                // 2. Handle Continuous Firing (Turret Logic)
                if (straightFireCooldown > 0) {
                    straightFireCooldown--;
                } else {
                    // Fire!
                    this.performStraightAttack();
                    // Reset timer (30 ticks = 1.5 seconds)
                    straightFireCooldown = 30;
                }
            }
        }
    }

    private void lockRotationToDirection() {
        // Get the direction the block/entity is technically facing
        Direction facing = this.getDirection();
        float yaw = facing.toYRot();

        // Force all rotation variables to this yaw so it doesn't spin
        this.setYRot(yaw);
        this.yHeadRot = yaw;
        this.yBodyRot = yaw;
        this.yHeadRotO = yaw;
        this.yBodyRotO = yaw;
    }

    // This handles the shooting for Straight Mode
    private void performStraightAttack() {
        PeaProjectile pea = new PeaProjectile(this.level(), this);
        pea.setItem(new ItemStack(ModItems.PEA.get()));

        Direction facing = this.getDirection();

        // Shoot straight along the axis
        pea.shoot(facing.getStepX(), 0.3, facing.getStepZ(), 1.6F, 0.0F);

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(pea);
    }

    // This handles the shooting for Free Range Mode (AI Targeted)
    @Override
    public void performRangedAttack(LivingEntity target, float velocity)
    {
        if (this.isStraightMode()) return;

        PeaProjectile pea = new PeaProjectile(this.level(), this);
        pea.setItem(new ItemStack(ModItems.PEA.get()));

        double d0 = target.getEyeY() - 1.1F;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - pea.getY();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;

        pea.shoot(d1, d2 + d4, d3, 1.6F, 5.0F);

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(pea);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide) {
                boolean newMode = !isStraightMode();
                setStraightMode(newMode);
                String modeName = newMode ? "Straight Line (Turret)" : "Free Range (Targeted)";
                player.displayClientMessage(Component.literal("Peashooter Mode: " + modeName), true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    public boolean isStraightMode() {
        return this.entityData.get(STRAIGHT_MODE);
    }

    public void setStraightMode(boolean isStraight) {
        this.entityData.set(STRAIGHT_MODE, isStraight);
    }

    // --- Inner Class for Custom Goal ---
    static class ModeAwareRangedAttackGoal extends RangedAttackGoal {
        private final PeaShooterEntity peashooter;

        public ModeAwareRangedAttackGoal(PeaShooterEntity shooter, double speedModifier, int attackInterval, float attackRadius) {
            super(shooter, speedModifier, attackInterval, attackRadius);
            this.peashooter = shooter;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !peashooter.isStraightMode();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !peashooter.isStraightMode();
        }
    }
}