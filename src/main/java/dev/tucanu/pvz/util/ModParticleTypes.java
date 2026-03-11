package dev.tucanu.pvz.util;

import dev.tucanu.pvz.PlantsvsZombies;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, PlantsvsZombies.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType>
            SUN = PARTICLE_TYPES.register("sun",
            () -> new SimpleParticleType(true));

}
