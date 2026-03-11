package dev.tucanu.pvz.item.custom;

import dev.tucanu.pvz.network.SunSyncPacket;
import dev.tucanu.pvz.util.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class SunItem extends Item
{
    public SunItem(Properties props) { super(props); }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        // 1. Only run on the Server and if it's a Player
        if (!level.isClientSide && entity instanceof ServerPlayer player) {

            // 2. Get the actual amount in this stack (e.g., if the player picked up a stack of 5)
            int stackCount = stack.getCount();

            // 3. If the stack is already empty/processed, stop here
            if (stackCount <= 0) return;

            int currentSun = player.getData(ModAttachments.SUN_STORAGE);

            // 4. Calculate new total (1 Sun per item)
            // If you want each item to be worth 25, use: stackCount * 25
            int amountToAdd = stackCount * 1;
            int nextSun = Math.min(currentSun + amountToAdd, 9990);

            // 5. Update and Sync
            player.setData(ModAttachments.SUN_STORAGE, nextSun);
            PacketDistributor.sendToPlayer(player, new SunSyncPacket(nextSun));

            // 6. CRITICAL: Set count to 0 immediately so it doesn't tick again next 1/20th of a second
            stack.setCount(0);
        }
    }
}
