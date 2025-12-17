package dev.tucanu.pvz;

import dev.tucanu.pvz.entity.custom.WallnutEntity;
import dev.tucanu.pvz.entity.custom.PeaShooterEntity;
import dev.tucanu.pvz.entity.custom.PotatoMineEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PlantsvsZombies.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<PotatoMineEntity>> POTATO_MINE =
            ENTITY_TYPES.register("potato_mine",
            () -> EntityType.Builder.of(PotatoMineEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.5f)
                    .build("potato_mine"));

    public static DeferredHolder<EntityType<?>, EntityType<PeaShooterEntity>> PEA_SHOOTER =
            ENTITY_TYPES.register("pea_shooter",
            () -> EntityType.Builder.of(PeaShooterEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1f)
                    .build("pea_shooter"));

    public static DeferredHolder<EntityType<?>, EntityType<WallnutEntity>> WALLNUT =
            ENTITY_TYPES.register("wallnut",
            () -> EntityType.Builder.of(WallnutEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.5f)
                    .build("wallnut"));


    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
