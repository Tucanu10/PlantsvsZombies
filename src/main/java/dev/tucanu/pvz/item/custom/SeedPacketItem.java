package dev.tucanu.pvz.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class SeedPacketItem extends DeferredSpawnEggItem {
    private final int sunCost;
    private final String health;
    private final String damage;
    private final String aoe;
    private final String description;

    public SeedPacketItem(Supplier<? extends EntityType<? extends Mob>> type, int sunCost,
                          String health, String damage, String aoe, String description, Properties properties) {
        super(type, 0xFFFFFF, 0xFFFFFF, properties);
        this.sunCost = sunCost;
        this.health = health;
        this.damage = damage;
        this.aoe = aoe;
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        // Header: Sun Cost
        tooltip.add(Component.literal("Cost: " + sunCost).withStyle(ChatFormatting.AQUA));

        // Stats block
        tooltip.add(Component.literal("Health: ").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(health).withStyle(ChatFormatting.WHITE)));

        tooltip.add(Component.literal("Damage: ").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(damage).withStyle(ChatFormatting.WHITE)));

        tooltip.add(Component.literal("Area: ").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(aoe).withStyle(ChatFormatting.WHITE)));

        // Spacer and Description
        tooltip.add(Component.empty());
        tooltip.add(Component.literal(description).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}