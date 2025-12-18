package dev.tucanu.pvz.item.armor;

import dev.tucanu.pvz.PlantsvsZombies;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import javax.annotation.Nullable;
import java.util.List;

public class CeramicPotArmorItem extends AbstractArmorItem
{
    public CeramicPotArmorItem(Properties properties)
    {
        super(ArmorMaterials.IRON, Type.HELMET, new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC)
                .attributes(createAttributes(4F, -3.4F)));
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, (double)((float)attackDamage), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public int getEnchantmentValue()
    {
        return 10;
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.IRON_INGOT || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.pvz.ceramic_pot.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean inner)
    {
        return makeCustomTextureLocation(PlantsvsZombies.MODID, "ceramic_pot");
    }
}
