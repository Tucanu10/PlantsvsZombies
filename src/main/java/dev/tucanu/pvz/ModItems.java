package dev.tucanu.pvz;


import dev.tucanu.pvz.item.custom.SeedPacketItem;
import dev.tucanu.pvz.item.armor.CeramicPotArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{
    public static DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(PlantsvsZombies.MODID);

    public static final DeferredHolder<Item, SeedPacketItem> SUNFLOWER = ITEMS.register("sunflower",
            () -> new SeedPacketItem(ModEntities.SUNFLOWER, 50,
                    "Low", "N/A", "Self",
                    "Produces extra sun to help you plant faster.",
                    new Item.Properties()));

    public static final DeferredHolder<Item, SeedPacketItem> PEA_SHOOTER = ITEMS.register("pea_shooter",
            () -> new SeedPacketItem(ModEntities.PEA_SHOOTER, 100,
                    "Low", "Normal", "Straight Line",
                    "Shoots peas at any zombie in its lane.",
                    new Item.Properties()));

    public static final DeferredHolder<Item, SeedPacketItem> WALLNUT = ITEMS.register("wallnut",
            () -> new SeedPacketItem(ModEntities.WALLNUT, 50,
                    "Very High", "N/A", "Self",
                    "A sturdy barrier that stops zombies in their tracks.",
                    new Item.Properties()));

    public static final DeferredHolder<Item, SeedPacketItem> POTATO_MINE = ITEMS.register("potato_mine",
            () -> new SeedPacketItem(ModEntities.POTATO_MINE, 25,
                    "Low", "Massive", "1x1 Tile",
                    "Explodes on contact, but needs time to arm itself.",
                    new Item.Properties()));

    public static final DeferredHolder<Item, SeedPacketItem> CHERRY_BOMB = ITEMS.register("cherry_bomb",
            () -> new SeedPacketItem(ModEntities.CHERRY_BOMB, 150,
                    "Invulnerable", "Massive", "3x3 Area",
                    "Blows up all zombies in a large area immediately.",
                    new Item.Properties()));

    public static final DeferredHolder<Item, SnowballItem> PEA = ITEMS.register("pea",
            () -> new SnowballItem(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, CeramicPotArmorItem> CERAMIC_POT = ITEMS.register("ceramic_pot",
            () -> new CeramicPotArmorItem(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
