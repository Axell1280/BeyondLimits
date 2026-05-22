package net.axell.createbeyondlimits.block;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.entity.*;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    BeyondLimits.MODID);

    public static final Supplier<BlockEntityType<AromaDisperserBlockEntity>> AROMA_DISPERSER =
            BLOCK_ENTITIES.register("aroma_disperser", () ->
                    BlockEntityType.Builder.of(
                            AromaDisperserBlockEntity::new,
                            ModBlocks.AROMA_DISPERSER_BLOCK.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<MaliceFragranceBlockEntity>> MALICE_FRAGRANCE =
            BLOCK_ENTITIES.register("malice_fragrance", () ->
                    BlockEntityType.Builder.of(
                            MaliceFragranceBlockEntity::new,
                            ModBlocks.FRAGRANCE_MALICE.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<CinderFragranceBlockEntity>> CINDER_FRAGRANCE =
            BLOCK_ENTITIES.register("cinder_fragrance", () ->
                    BlockEntityType.Builder.of(
                            CinderFragranceBlockEntity::new,
                            ModBlocks.FRAGRANCE_CINDER.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<BastionFragranceBlockEntity>> BASTION_FRAGRANCE =
            BLOCK_ENTITIES.register("bastion_fragrance", () ->
                    BlockEntityType.Builder.of(
                            BastionFragranceBlockEntity::new,
                            ModBlocks.FRAGRANCE_BASTION.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<IntensifiedAnchorBlockEntity>> INTENSIFIED_ANCHOR =
            BLOCK_ENTITIES.register("intensified_anchor", () ->
                    BlockEntityType.Builder.of(
                            IntensifiedAnchorBlockEntity::new,
                            ModBlocks.INTENSIFIED_ANCHOR.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<AnchorBlockEntity>> ANCHOR_BE =
            BLOCK_ENTITIES.register("anchor_be", () ->
                    BlockEntityType.Builder.of(
                            AnchorBlockEntity::new,
                            ModBlocks.ANCHOR.get()
                    ).build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}