package net.axell.createbeyondlimits.item;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.block.custom.CheeseType;
import net.axell.createbeyondlimits.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BeyondLimits.MOD_ID);

    private static FoodProperties cheeseFood(int nutrition) {
        return new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationMod(0.6f)
                .alwaysEat()
                .build();
    }

    private static FoodProperties kernelFood() {
        return new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.2f)
                .alwaysEat()
                .build();
    }

    //headers


    public static final RegistryObject<Item> KINETIC_HEADER = ITEMS.register("kinetic_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    public static final RegistryObject<Item> CHEESE_HEADER = ITEMS.register("cheese_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    public static final RegistryObject<Item> NATURE_HEADER = ITEMS.register("nature_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    public static final RegistryObject<Item> DYSTHEISM_HEADER = ITEMS.register("dystheism_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    public static final RegistryObject<Item> TOTEM_HEADER = ITEMS.register("totem_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    public static final RegistryObject<Item> INGREDIENT_HEADER = ITEMS.register("ingredient_header",
            () -> new CreativeHeaderItem(new Item.Properties()));

    // Example normal items
    public static final RegistryObject<Item> NETHER_STAR_FRAGMENT = ITEMS.register("nether_star_fragment",
            () -> new NetherStarFragmentItem(new Item.Properties()
                    .stacksTo(16)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<Item> DRAINED_NETHER_STAR_FRAGMENT = ITEMS.register("drained_nether_star_fragment",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> UNSTABLE_QUARTZ = ITEMS.register("unstable_quartz",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> BEYOND_LIMITS = ITEMS.register("beyond_limits",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INCOMPLETE_FORGED_NETHERITE = ITEMS.register("incomplete_forged_netherite",
            () -> new Item(new Item.Properties()
                    .fireResistant()
            ));

    public static final RegistryObject<Item> PRESSURE_CORE = ITEMS.register("pressure_core",
            () -> new PressureCoreItem(new Item.Properties()
                    .stacksTo(8)
                    .rarity(Rarity.RARE)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> INCOMPLETE_COPPER_PIECE = ITEMS.register("incomplete_copper_piece",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .rarity(Rarity.UNCOMMON)
            ));

    public static final RegistryObject<Item> FABRICATED_ELYTRA_PIECE = ITEMS.register("fabricated_elytra_piece",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
            ));

    public static final RegistryObject<Item> WING =ITEMS.register("wing",
            () -> new WingItem(new Item.Properties()
                    .stacksTo(64)
                    .rarity(Rarity.COMMON)
            ));

    public static final RegistryObject<Item> TOTEM_REGEN = ITEMS.register("totem_regen",
            () -> new RegenTotemItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant()
            ));
    public static final RegistryObject<Item> TOTEM_STRENGTH = ITEMS.register("totem_strength",
            () -> new StrengthTotemItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant()
            ));
    public static final RegistryObject<Item> TOTEM_SPEED = ITEMS.register("totem_speed",
            () -> new SpeedTotemItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant()
            ));

    public static final RegistryObject<Item> SUNFLOWER_SEEDS = ITEMS.register("sunflower_seed",
            () -> new ItemNameBlockItem(ModBlocks.DOMESTIC_SUNFLOWER.get(), new Item.Properties()));

    public static final RegistryObject<Item> ROSEMARY = ITEMS.register("rosemary",
            () -> new ItemNameBlockItem(ModBlocks.ROSEMARY.get(), new Item.Properties()));

    public static final RegistryObject<Item> SUNFLOWER_KERNAL = ITEMS.register("sunflower_kernal",
            () -> new Item(new Item.Properties()
                    .food(kernelFood())
            ));

    public static final RegistryObject<Item> SHEETED_BASE = ITEMS.register("sheeted_base",
            () -> new Item(new Item.Properties()
            ));
    public static final RegistryObject<Item> SHEETED_PILLAR = ITEMS.register("sheeted_pillar",
            () -> new Item(new Item.Properties()
            ));


    // --- Block Items ---
    public static final RegistryObject<Item> BASE_FRAGRANCE_ITEM = ITEMS.register("fragrance",
            () -> new BlockItem(ModBlocks.BASE_FRAGRANCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ANCHOR_ITEM = ITEMS.register("anchor",
            () -> new BlockItem(ModBlocks.ANCHOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> SUPERPHOSPHATE = ITEMS.register("superphosphate",
            () -> new SuperphosphateItem(new Item.Properties()
                    .stacksTo(64)
            ));
    public static final RegistryObject<Item> RAW_PHOSPHATE = ITEMS.register("raw_phosphate",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
            ));
    public static final RegistryObject<Item> PHOSPHATE_ORE = ITEMS.register("phosphate_ore",
            () -> new BlockItem(ModBlocks.PHOSPHATE_ORE.get(),
                    new Item.Properties()
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> INCOMPLETE_DISPERSER = ITEMS.register("incomplete_disperser",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
            ));
    public static final RegistryObject<Item> AROMA_DISPERSER_ITEM = ITEMS.register("aroma_disperser",
            () -> new BlockItem(ModBlocks.AROMA_DISPERSER_BLOCK.get(), new Item.Properties()
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> DEEPSLATE_PHOSPHATE_ORE = ITEMS.register("deepslate_phosphate_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get(),
                    new Item.Properties()
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> BLUE_CHEESE_BLOCK_ITEM = ITEMS.register("blue_cheese_block",
            () -> new BlockItem(ModBlocks.BLUE_CHEESE_BLOCK.get(),
                    new Item.Properties()));

    public static final RegistryObject<Item> CHEESE_BLOCK_ITEM = ITEMS.register("cheese_block",
            () -> new BlockItem(ModBlocks.CHEESE_BLOCK.get(),
                    new Item.Properties()));

    public static final RegistryObject<Item> PARMESAN_CHEESE_BLOCK_ITEM = ITEMS.register("parmesan_cheese_block",
            () -> new BlockItem(ModBlocks.PARMESAN_CHEESE_BLOCK.get(),
                    new Item.Properties()));

    public static final RegistryObject<Item> DOMESTIC_SUNFLOWER_ITEM = ITEMS.register("domestic_sunflower",
            () -> new BlockItem(ModBlocks.DOMESTIC_SUNFLOWER.get(),
                    new Item.Properties()));

    public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
            () -> new CheeseSliceItem(new Item.Properties()
                    .food(cheeseFood(6)),
                    CheeseType.NORMAL
            ));
    public static final RegistryObject<Item> BLUE_CHEESE_SLICE = ITEMS.register("blue_cheese_slice",
            () -> new CheeseSliceItem(new Item.Properties()
                    .food(cheeseFood(8)),
                    CheeseType.BLUE
            ));
    public static final RegistryObject<Item> PARMESAN_CHEESE_SLICE = ITEMS.register("parmesan_slice",
            () -> new CheeseSliceItem(new Item.Properties()
                    .food(cheeseFood(10)),
                    CheeseType.PARMESAN
            ));

    public static final RegistryObject<Item> FRAGRANCE_CINDER = ITEMS.register("fragrance_cinder",
            () -> new BlockItem(ModBlocks.FRAGRANCE_CINDER.get(),
                    new Item.Properties()
                            .rarity(Rarity.RARE)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> FRAGRANCE_MALICE = ITEMS.register("fragrance_malice",
            () -> new BlockItem(ModBlocks.FRAGRANCE_MALICE.get(),
                    new Item.Properties()
                            .rarity(Rarity.RARE)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> FRAGRANCE_BASTION = ITEMS.register("fragrance_bastion",
            () -> new BlockItem(ModBlocks.FRAGRANCE_BASTION.get(),
                    new Item.Properties()
                            .rarity(Rarity.RARE)
                            .fireResistant()
            ));

    public static final RegistryObject<Item> INTENSIFIED_ANCHOR = ITEMS.register("intensified_anchor",
            () -> new BlockItem(ModBlocks.INTENSIFIED_ANCHOR.get(),
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            ));

    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
