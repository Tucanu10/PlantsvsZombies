package dev.tucanu.pvz.network;

import dev.tucanu.pvz.util.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static dev.tucanu.pvz.PlantsvsZombies.MODID;

public record SunSyncPacket(int amount) implements CustomPacketPayload
{
    public static final Type<SunSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "sun_sync"));

    public static final StreamCodec<ByteBuf, SunSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SunSyncPacket::amount,
            SunSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    // This runs on the player's computer when the packet arrives
    public static void handle(final SunSyncPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            // Update the client-side attachment data
            context.player().setData(ModAttachments.SUN_STORAGE, payload.amount());
        });
    }
}