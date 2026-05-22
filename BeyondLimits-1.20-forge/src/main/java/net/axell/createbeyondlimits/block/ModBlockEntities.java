package net.axell.createbeyondlimits.block;

import net.axell.createbeyondlimits.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "createbeyondlimits");


    public static final RegistryObject<BlockEntityType<AromaDisperserBlockEntity>> AROMA_DISPERSER =
            BLOCK_ENTITIES.register("aroma_disperser", () ->
                    BlockEntityType.Builder.of(AromaDisperserBlockEntity::new,
                            ModBlocks.AROMA_DISPERSER_BLOCK.get() // <-- Added _BLOCK here
                    ).build(null)
            );


    public static final RegistryObject<BlockEntityType<MaliceFragranceBlockEntity>> MALICE_FRAGRANCE =
            BLOCK_ENTITIES.register("malice_fragrance", () ->
                    BlockEntityType.Builder.of(MaliceFragranceBlockEntity::new, ModBlocks.FRAGRANCE_MALICE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CinderFragranceBlockEntity>> CINDER_FRAGRANCE =
            BLOCK_ENTITIES.register("cinder_fragrance", () ->
                    BlockEntityType.Builder.of(CinderFragranceBlockEntity::new, ModBlocks.FRAGRANCE_CINDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BastionFragranceBlockEntity>> BASTION_FRAGRANCE =
            BLOCK_ENTITIES.register("bastion_fragrance", () ->
                    BlockEntityType.Builder.of(BastionFragranceBlockEntity::new, ModBlocks.FRAGRANCE_BASTION.get()).build(null));


    public static final RegistryObject<BlockEntityType<IntensifiedAnchorBlockEntity>> INTENSIFIED_ANCHOR =
            BLOCK_ENTITIES.register("intensified_anchor", () ->
                    BlockEntityType.Builder.of(IntensifiedAnchorBlockEntity::new, ModBlocks.INTENSIFIED_ANCHOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<AnchorBlockEntity>> ANCHOR_BE =
            BLOCK_ENTITIES.register("anchor_be", () ->
                    BlockEntityType.Builder.of(AnchorBlockEntity::new, ModBlocks.ANCHOR.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}