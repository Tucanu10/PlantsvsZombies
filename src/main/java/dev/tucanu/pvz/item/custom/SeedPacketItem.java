package dev.tucanu.pvz.item.custom;

import dev.tucanu.pvz.entity.custom.PeaShooterEntity;
import dev.tucanu.pvz.entity.custom.PlantEntity;
import dev.tucanu.pvz.network.SunSyncPacket;
import dev.tucanu.pvz.util.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.network.PacketDistributor;

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
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;

        if (canAffordAndConsume(player, level)) {
            InteractionResult result = super.useOn(context);

            if (result.consumesAction() && !level.isClientSide) {
                // Find the spawned plant in a small area
                AABB area = new AABB(context.getClickedPos()).inflate(1.5);
                List<PlantEntity> plants = level.getEntitiesOfClass(PlantEntity.class, area);

                for (PlantEntity plant : plants) {
                    if (plant.getOwnerUUID() == null) {
                        plant.setOwnerUUID(player.getUUID());

                        if (plant instanceof PeaShooterEntity peaShooter) {
                            // Make it face the way the player is looking
                            peaShooter.setOrientation(player.getDirection());
                        }
                    }
                }
            }
            return result;
        } else {
            if (level.isClientSide) {
                player.displayClientMessage(Component.literal("Not enough Sun!").withStyle(ChatFormatting.RED), true);
            }
        }
        return InteractionResult.FAIL;
    }

    private boolean canAffordAndConsume(Player player, Level level) {
        int current = player.getData(ModAttachments.SUN_STORAGE);
        if (current >= this.sunCost || player.getAbilities().instabuild) {
            if (!level.isClientSide && !player.getAbilities().instabuild) {
                int next = current - this.sunCost;
                player.setData(ModAttachments.SUN_STORAGE, next);
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SunSyncPacket(next));
            }
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Cost: " + sunCost).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Health: ").withStyle(ChatFormatting.GOLD).append(Component.literal(health).withStyle(ChatFormatting.WHITE)));
        tooltip.add(Component.literal("Damage: ").withStyle(ChatFormatting.GOLD).append(Component.literal(damage).withStyle(ChatFormatting.WHITE)));
        tooltip.add(Component.literal("Area: ").withStyle(ChatFormatting.GOLD).append(Component.literal(aoe).withStyle(ChatFormatting.WHITE)));
        tooltip.add(Component.empty());
        tooltip.add(Component.literal(description).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}