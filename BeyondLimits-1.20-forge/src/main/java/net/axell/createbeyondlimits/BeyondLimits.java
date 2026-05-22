package net.axell.createbeyondlimits;

import com.mojang.logging.LogUtils;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.axell.createbeyondlimits.client.CBLPartialModels;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.config.ModConfigs;
import net.axell.createbeyondlimits.item.ModCreativeModeTabs;
import net.axell.createbeyondlimits.item.Moditems;
import net.axell.createbeyondlimits.networking.ModNetworking;
import net.axell.createbeyondlimits.ponder.BeyondLimitsPonderPlugin;
import net.axell.createbeyondlimits.sound.ModSounds;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.axell.createbeyondlimits.client.render.AromaDisperserRenderer;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BeyondLimits.MOD_ID)
public class BeyondLimits {
    public static final String MOD_ID = "createbeyondlimits";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static int breedingChance = 300;
    public static GameRules.Key<GameRules.BooleanValue> RULE_NATURAL_BREEDING;

    public BeyondLimits(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModNetworking.register();
        ModBlockEntities.register(modEventBus);
        ModBlocks.register(modEventBus);
        Moditems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModSounds.register(modEventBus);
        ModConfigs.register();


        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerWorldGen);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RULE_NATURAL_BREEDING = GameRules.register("doNaturalBreeding",
                    GameRules.Category.SPAWNING, GameRules.BooleanValue.create(true));
        });
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraft.commands.Commands.literal("beyondlimits")
                .then(net.minecraft.commands.Commands.literal("breedingFrequency")
                        .requires(source -> source.hasPermission(2))
                        .then(net.minecraft.commands.Commands.argument("value", com.mojang.brigadier.arguments.IntegerArgumentType.integer(1))
                                .executes(context -> {
                                    int newValue = com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(context, "value");
                                    BeyondLimits.breedingChance = newValue;

                                    context.getSource().sendSuccess(() ->
                                            net.minecraft.network.chat.Component.literal("§bBreeding chance is now 1 in " + newValue), true);
                                    return 1;
                                })
                        )
                )
        );
    }


    private void registerWorldGen(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Datapack loaded
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                PonderIndex.addPlugin(new BeyondLimitsPonderPlugin());

                // Registers your 3D filter items and your inner rotating cogwheel
                BlockEntityRenderers.register(ModBlockEntities.AROMA_DISPERSER.get(), AromaDisperserRenderer::new);
                CBLPartialModels.init();
            });
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        }
    }
}