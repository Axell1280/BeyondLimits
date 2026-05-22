package net.axell.createbeyondlimits.block;

import com.simibubi.create.AllSoundEvents;
import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.custom.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BeyondLimits.MOD_ID);



    public static final RegistryObject<Block> AROMA_DISPERSER_BLOCK = BLOCKS.register("aroma_disperser",
            () ->new AromaDisperserBlock(
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .strength(1.5f,2f)
                            .mapColor(MapColor.COLOR_YELLOW)
                            .noOcclusion()
                            .sound(SoundType.METAL)
                            .lightLevel(state -> 10)
                               ));
    // --- OTHER BLOCKS ---

    public static final RegistryObject<Block> BLUE_CHEESE_BLOCK = BLOCKS.register("blue_cheese_block",
            () -> new CheeseBlock(
                    Block.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_BLUE),
                    CheeseType.BLUE
            ));

    public static final RegistryObject<Block> CHEESE_BLOCK = BLOCKS.register("cheese_block",
            () -> new CheeseBlock(
                    Block.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_ORANGE),
                    CheeseType.NORMAL
            ));

    public static final RegistryObject<Block> PARMESAN_CHEESE_BLOCK = BLOCKS.register("parmesan_cheese_block",
            () -> new CheeseBlock(
                    Block.Properties.of()
                            .strength(0.5f)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_YELLOW),
                    CheeseType.PARMESAN
            ));

    public static final RegistryObject<Block> DOMESTIC_SUNFLOWER = BLOCKS.register("domestic_sunflower",
            () -> new DomesticatedSunflowerBlock(
                    BlockBehaviour.Properties.copy(Blocks.WHEAT)
                            .noOcclusion()
                            .sound(SoundType.CROP)
            ));

    public static final RegistryObject<Block> ROSEMARY = BLOCKS.register("rosemary",
            () -> new RosemaryBlock(
                    BlockBehaviour.Properties.copy(Blocks.WHEAT)
                            .noOcclusion()
                            .sound(SoundType.CROP)
            ));

    public static final RegistryObject<Block> PHOSPHATE_ORE = BLOCKS.register("phosphate_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy((Blocks.STONE))
                            .strength(2.0f)
                            .sound(SoundType.STONE)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(2, 4)
            ));

    public static final RegistryObject<Block> DEEPSLATE_PHOSPHATE_ORE = BLOCKS.register("deepslate_phosphate_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                            .strength(3.0f)
                            .sound(SoundType.DEEPSLATE)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(4, 8)
            ));

    public static final RegistryObject<Block> ANCHOR = BLOCKS.register("anchor",
            () -> new AnchorBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
                            .strength(1.5f,3.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .sound(SoundType.ANVIL)
            ));

    public static final RegistryObject<Block> BASE_FRAGRANCE = BLOCKS.register("fragrance",
            () -> new FragranceBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
                            .strength(1.5f, 3.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .sound(SoundType.ANVIL)
            ));

    public static final RegistryObject<Block> FRAGRANCE_CINDER = BLOCKS.register("fragrance_cinder",
            () -> new CinderFragranceBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final RegistryObject<Block> FRAGRANCE_MALICE = BLOCKS.register("fragrance_malice",
            () -> new MaliceFragranceBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final RegistryObject<Block> FRAGRANCE_BASTION = BLOCKS.register("fragrance_bastion",
            () -> new BastionFragranceBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .lightLevel(state -> 8)
                            .sound(SoundType.NETHERITE_BLOCK)
            ));

    public static final RegistryObject<Block> INTENSIFIED_ANCHOR = BLOCKS.register("intensified_anchor",
            () -> new IntensifiedAnchorBlock(Block.Properties.of()
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