package dev.tucanu.pvz.event;

import dev.tucanu.pvz.ModEntities;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = PlantsvsZombies.MODID)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void onZombieSpawn(EntityJoinLevelEvent event)
    {
        if(event.getEntity() instanceof Monster monster)
        {
            monster.targetSelector.addGoal(3,
                    new NearestAttackableTargetGoal<>(
                            monster, PlantEntity.class,
                            true));
        }
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.POTATO_MINE.get(), PotatoMineEntity.createAttributes().build());
        event.put(ModEntities.PEA_SHOOTER.get(), PeaShooterEntity.createAttributes().build());
        event.put(ModEntities.WALLNUT.get(), WallnutEntity.createAttributes().build());
        event.put(ModEntities.CHERRY_BOMB.get(), CherryBombEntity.createAttributes().build());
        event.put(ModEntities.SUNFLOWER.get(), SunflowerEntity.createAttributes().build());
    }
}
