package dev.tucanu.pvz.event;

import dev.tucanu.pvz.ModEntities;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.client.PotatoMine.PotatoMineModel;
import dev.tucanu.pvz.entity.client.PeaShooter.PeaShooterModel;
import dev.tucanu.pvz.entity.custom.PeaShooterEntity;
import dev.tucanu.pvz.entity.custom.PotatoMineEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = PlantsvsZombies.MODID)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(PotatoMineModel.LAYER_LOCATION, PotatoMineModel::createBodyLayer);
        event.registerLayerDefinition(PeaShooterModel.LAYER_LOCATION, PeaShooterModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.POTATO_MINE.get(), PotatoMineEntity.createAttributes().build());
        event.put(ModEntities.PEA_SHOOTER.get(), PeaShooterEntity.createAttributes().build());
    }
}
