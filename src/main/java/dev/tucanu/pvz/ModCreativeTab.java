package dev.tucanu.pvz;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import static dev.tucanu.pvz.PlantsvsZombies.CREATIVE_MODE_TABS;

public class ModCreativeTab {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PLANTS_VS_ZOMBIES_TAB =
            CREATIVE_MODE_TABS.register("pvz_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pvz"))
                    // Add an icon so the tab has a visual handle
                    .icon(() -> new ItemStack(ModItems.PEA_SHOOTER.get()))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CERAMIC_POT.get());
                        output.accept(ModItems.PEA.get());
                        output.accept(ModItems.WALLNUT.get());
                        output.accept(ModItems.POTATO_MINE.get());
                        output.accept(ModItems.PEA_SHOOTER.get());
                    }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
