package net.axell.createbeyondlimits.block;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.custom.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BeyondLimits.MODID);

    public static final Supplier<Block> AROMA_DISPERSER_BLOCK = BLOCKS.register("aroma_disperser",
            () -> new AromaDisperserBlock(
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(1.5f, 2f)
                            .mapColor(MapColor.COLOR_YELLOW)
                            .noOcclusion()
                            .sound(SoundType.METAL)
                            .lightLevel(state -> 10)
            ));

    // --- OTHER BLOCKS ---

    public static final Supplier<Block> BLUE_CHEESE_BLOCK = BLOCKS.register("blue_cheese_block",
            () -> new CheeseBlock(
                    BlockBehaviour.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_BLUE),
                    CheeseType.BLUE
            ));

    public static final Supplier<Block> CHEESE_BLOCK = BLOCKS.register("cheese_block",
            () -> new CheeseBlock(
                    BlockBehaviour.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_ORANGE),
                    CheeseType.NORMAL
            ));

    public static final Supplier<Block> PARMESAN_CHEESE_BLOCK = BLOCKS.register("parmesan_cheese_block",
            () -> new CheeseBlock(
                    BlockBehaviour.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_YELLOW),
                    CheeseType.PARMESAN
            ));

    public static final Supplier<Block> DOMESTIC_SUNFLOWER = BLOCKS.register("domestic_sunflower",
            () -> new DomesticatedSunflowerBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)
                            .noOcclusion()
                            .sound(SoundType.CROP)
            ));



    public static final Supplier<Block> PHOSPHATE_ORE = BLOCKS.register("phosphate_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(2, 4),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                            .strength(2.0f)
                            .sound(SoundType.STONE)
                            .requiresCorrectToolForDrops()
            ));

    public static final Supplier<Block> DEEPSLATE_PHOSPHATE_ORE = BLOCKS.register("deepslate_phosphate_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 8),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                            .strength(3.0f)
                            .sound(SoundType.DEEPSLATE)
                            .requiresCorrectToolForDrops()
            ));

    public static final Supplier<Block> ANCHOR = BLOCKS.register("anchor",
            () -> new AnchorBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
                            .strength(1.5f, 3.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .sound(SoundType.ANVIL)
            ));

    public static final Supplier<Block> BASE_FRAGRANCE = BLOCKS.register("fragrance",
            () -> new FragranceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
                            .strength(1.5f, 3.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .sound(SoundType.ANVIL)
            ));

    public static final Supplier<Block> FRAGRANCE_CINDER = BLOCKS.register("fragrance_cinder",
            () -> new CinderFragranceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final Supplier<Block> FRAGRANCE_MALICE = BLOCKS.register("fragrance_malice",
            () -> new MaliceFragranceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final Supplier<Block> FRAGRANCE_BASTION = BLOCKS.register("fragrance_bastion",
            () -> new BastionFragranceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final Supplier<Block> INTENSIFIED_ANCHOR = BLOCKS.register("intensified_anchor",
            () -> new IntensifiedAnchorBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .strength(1000.0f, 12.0f)
                            .noOcclusion()
                            .dynamicShape()
                            .isValidSpawn((state, getter, pos, type) -> false)
                            .lightLevel(state -> 15)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}