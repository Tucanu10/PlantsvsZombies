package dev.tucanu.pvz;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import static dev.tucanu.pvz.PlantsvsZombies.CREATIVE_MODE_TABS;

public class ModCreativeTab
{
    // Creates a creative tab with the id "pvz:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PLANTS_VS_ZOMBIES_TAB = CREATIVE_MODE_TABS.register("pvz_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.pvz")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .displayItems((parameters, output) -> {
                output.accept(ModItems.WALLNUT.get());
                output.accept(ModItems.POTATO_MINE.get());
                output.accept(ModItems.PEA_SHOOTER.get());
            }).build());


    public static void register(IEventBus bus)
    {
        CREATIVE_MODE_TABS.register(bus);
    }
}
