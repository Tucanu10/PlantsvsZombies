package dev.tucanu.pvz.event;

import dev.tucanu.pvz.ModEntities;
import dev.tucanu.pvz.ModItems;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.client.PeaShooter.PeaShooterModel;
import dev.tucanu.pvz.entity.client.PeaShooter.PeaShooterRenderer;
import dev.tucanu.pvz.entity.client.PotatoMine.PotatoMineModel;
import dev.tucanu.pvz.entity.client.PotatoMine.PotatoMineRenderer;
import dev.tucanu.pvz.entity.client.Wallnut.WallnutModel;
import dev.tucanu.pvz.entity.client.Wallnut.WallnutRenderer;
import dev.tucanu.pvz.item.armor.client.ArmorClientExtension;
import dev.tucanu.pvz.item.armor.client.model.CeramicPotModel;
import dev.tucanu.pvz.item.armor.client.provider.SimpleModelProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = PlantsvsZombies.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerItem(new ArmorClientExtension
                (new SimpleModelProvider(CeramicPotModel::createBodyLayer, CeramicPotModel::new)), ModItems.CERAMIC_POT);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PeaShooterModel.LAYER_LOCATION, PeaShooterModel::createBodyLayer);
        event.registerLayerDefinition(PotatoMineModel.LAYER_LOCATION, PotatoMineModel::createBodyLayer);
        event.registerLayerDefinition(WallnutModel.LAYER_LOCATION, WallnutModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.PEA_SHOOTER.get(), PeaShooterRenderer::new);
        event.registerEntityRenderer(ModEntities.POTATO_MINE.get(), PotatoMineRenderer::new);
        event.registerEntityRenderer(ModEntities.WALLNUT.get(), WallnutRenderer::new);
    }
}
