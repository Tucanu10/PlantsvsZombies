package dev.tucanu.pvz.util;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "pvz");

    public static final Supplier<AttachmentType<Integer>> SUN_STORAGE =
            ATTACHMENT_TYPES.register("sun_storage", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT) // Allows saving to disk
                    .copyOnDeath()      // Keeps sun after dying (PvZ style)
                    .build());
}
