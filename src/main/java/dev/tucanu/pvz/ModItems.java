package dev.tucanu.pvz;


import dev.tucanu.pvz.item.armor.CeramicPotArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{
    public static DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(PlantsvsZombies.MODID);

    public static final DeferredHolder<Item, DeferredSpawnEggItem> POTATO_MINE = ITEMS.register("potato_mine",
            () -> new DeferredSpawnEggItem(
                    ModEntities.POTATO_MINE,
                    0xFFFFFF,
                    0x000000,
                    new Item.Properties().stacksTo(64)
            ));

    public static final DeferredHolder<Item, DeferredSpawnEggItem> PEA_SHOOTER = ITEMS.register("pea_shooter",
            () -> new DeferredSpawnEggItem(
                    ModEntities.PEA_SHOOTER,
                    0xFFFFFF,
                    0x000000,
                    new Item.Properties().stacksTo(64)
            ));

    public static final DeferredHolder<Item, DeferredSpawnEggItem> WALLNUT = ITEMS.register("wallnut",
            () -> new DeferredSpawnEggItem(
                    ModEntities.WALLNUT,
                    0xFFFFFF,
                    0x000000,
                    new Item.Properties().stacksTo(64)
            ));

    public static final DeferredHolder<Item, SnowballItem> PEA = ITEMS.register("pea",
            () -> new SnowballItem(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, CeramicPotArmorItem> CERAMIC_POT = ITEMS.register("ceramic_pot",
            () -> new CeramicPotArmorItem(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
