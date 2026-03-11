package dev.tucanu.pvz;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.logging.LogUtils;
import dev.tucanu.pvz.gui.HudOverlay;
import dev.tucanu.pvz.network.SunSyncPacket;
import dev.tucanu.pvz.util.*;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PlantsvsZombies.MODID)
public class PlantsvsZombies {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pvz";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "pvz" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "pvz" namespace
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "pvz" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PlantsvsZombies(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerGuiLayers);

        BLOCKS.register(modEventBus);
        ModParticleTypes.PARTICLE_TYPES.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModEntities.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (PlantsvsZombies) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1"); // Version string

        // Register our packet to be sent to the Client
        registrar.playToClient(
                SunSyncPacket.TYPE,
                SunSyncPacket.STREAM_CODEC,
                SunSyncPacket::handle
        );
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath("pvz", "sun_hud"),
                HudOverlay::render
        );
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("sun")
                .then(Commands.literal("reset")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.setData(ModAttachments.SUN_STORAGE, 0);
                            PacketDistributor.sendToPlayer(player, new SunSyncPacket(0));
                            context.getSource().sendSuccess(() -> Component.literal("Sun counter reset!"), true);
                            return 1;
                        })
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, 9990))
                                .executes(context -> {
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                    player.setData(ModAttachments.SUN_STORAGE, amount);
                                    PacketDistributor.sendToPlayer(player, new SunSyncPacket(amount));

                                    context.getSource().sendSuccess(() -> Component.literal("Sun set to: " + amount), true);
                                    return 1;
                                })
                        )
                )
        );
    }
}
