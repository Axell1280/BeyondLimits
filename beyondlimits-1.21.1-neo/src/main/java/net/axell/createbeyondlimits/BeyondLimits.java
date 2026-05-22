package net.axell.createbeyondlimits;

import com.mojang.logging.LogUtils;
import net.axell.createbeyondlimits.advancement.ModCriteria;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.client.CBLPartialModels;
import net.axell.createbeyondlimits.client.render.AromaDisperserRenderer;
import net.axell.createbeyondlimits.config.ModConfigs;
import net.axell.createbeyondlimits.item.ModCreativeModeTabs;
import net.axell.createbeyondlimits.item.Moditems;
import net.axell.createbeyondlimits.ponder.BeyondLimitsPonderPlugin;
import net.axell.createbeyondlimits.sound.ModSounds;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.GameRules;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(BeyondLimits.MODID)
public class BeyondLimits {
    public static final String MODID = "createbeyondlimits";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static int breedingChance = 300;
    public static GameRules.Key<GameRules.BooleanValue> RULE_NATURAL_BREEDING;

    public BeyondLimits(IEventBus modEventBus, ModContainer modContainer) {
        // Registering modern NeoForge subsystems
        ModBlockEntities.register(modEventBus);
        ModBlocks.register(modEventBus);
        Moditems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModSounds.register(modEventBus);
        ModCriteria.register(modEventBus);

        // Configuration loader registration
        ModConfigs.register();

        // Mod bus setup listeners
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerWorldGen);

        // Global Game bus setup registration
        NeoForge.EVENT_BUS.register(this);
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
            // World generation placement rules go here
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Beyond Limits server initialization complete.");
    }

    // Fixed package layout for the modern EventBusSubscriber import on Client setup
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                // Initialize custom Ponder layouts
                PonderIndex.addPlugin(new BeyondLimitsPonderPlugin());

                // Register 1.21.1 Block Entity renderers natively
                BlockEntityRenderers.register(ModBlockEntities.AROMA_DISPERSER.get(), AromaDisperserRenderer::new);

                // Load assets handled by Create engine partial configurations
                CBLPartialModels.init();
            });
        }
    }
}