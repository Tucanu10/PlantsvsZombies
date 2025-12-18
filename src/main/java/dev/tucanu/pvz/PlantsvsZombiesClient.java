package dev.tucanu.pvz;

import dev.tucanu.pvz.entity.client.PeaShooter.PeaShooterRenderer;
import dev.tucanu.pvz.entity.client.PotatoMine.PotatoMineRenderer;
import dev.tucanu.pvz.entity.client.Wallnut.WallnutRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static com.mojang.text2speech.Narrator.LOGGER;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = PlantsvsZombies.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = PlantsvsZombies.MODID, value = Dist.CLIENT)
public class PlantsvsZombiesClient {
    public PlantsvsZombiesClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Gathering sun for plants!");

            EntityRenderers.register(ModEntities.POTATO_MINE.get(), PotatoMineRenderer::new);
            EntityRenderers.register(ModEntities.PEA_SHOOTER.get(), PeaShooterRenderer::new);
            EntityRenderers.register(ModEntities.WALLNUT.get(), WallnutRenderer::new);

        }
    }
