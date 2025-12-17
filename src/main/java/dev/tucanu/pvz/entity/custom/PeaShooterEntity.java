package dev.tucanu.pvz.entity.custom;

import dev.tucanu.pvz.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PeaShooterEntity extends PlantEntity implements RangedAttackMob
{
    public PeaShooterEntity(EntityType<? extends PeaShooterEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PlantEntity.createAttributes();
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();

        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 30, 15.0F));
    }


    @Override
    public void performRangedAttack(LivingEntity target, float velocity)
    {
        PeaProjectile pea = new PeaProjectile(this.level(), this);
        double d0 = target.getEyeY() - 1.1F;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - pea.getY();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        pea.shoot(d1, d2 + d4, d3, 1.6F, 5.0F);

        pea.setItem(new ItemStack(ModItems.PEA.get()));
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(pea);
    }
}
