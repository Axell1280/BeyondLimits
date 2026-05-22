package net.axell.createbeyondlimits.item;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondLimits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondLimits.MOD_ID);

    public static final ResourceLocation BEYOND_LIMITS_TAB_RL =
            new ResourceLocation(BeyondLimits.MOD_ID, "beyond_limits_tab");

    public static final RegistryObject<CreativeModeTab> BEYOND_LIMITS_TAB = CREATIVE_MODE_TABS.register("beyond_limits_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new net.minecraft.world.item.ItemStack(Moditems.BEYOND_LIMITS.get()))
                    .title(Component.translatable("creativetab.beyond_limits_tab"))
                    .displayItems((params, output) -> {
                        // Kinetic
                        output.accept(ModBlocks.AROMA_DISPERSER_BLOCK.get().asItem());
                        // Dystheism
                        output.accept(Moditems.FRAGRANCE_CINDER.get());
                        output.accept(Moditems.FRAGRANCE_BASTION.get());
                        output.accept(Moditems.FRAGRANCE_MALICE.get());
                        output.accept(Moditems.INTENSIFIED_ANCHOR.get());
                        output.accept(Moditems.BASE_FRAGRANCE_ITEM.get());
                        output.accept(Moditems.ANCHOR_ITEM.get());
                        // Nature
                        output.accept(Moditems.SUNFLOWER_SEEDS.get());
                        output.accept(Moditems.SUNFLOWER_KERNAL.get());
                        output.accept(ModBlocks.PHOSPHATE_ORE.get().asItem());
                        output.accept(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get().asItem());
                        output.accept(Moditems.RAW_PHOSPHATE.get());
                        output.accept(Moditems.SUPERPHOSPHATE.get());
                        // Cheese
                        output.accept(Moditems.CHEESE_SLICE.get());
                        output.accept(Moditems.BLUE_CHEESE_SLICE.get());
                        output.accept(Moditems.PARMESAN_CHEESE_SLICE.get());
                        output.accept(Moditems.CHEESE_BLOCK_ITEM.get());
                        output.accept(Moditems.BLUE_CHEESE_BLOCK_ITEM.get());
                        output.accept(Moditems.PARMESAN_CHEESE_BLOCK_ITEM.get());
                        // Totems
                        output.accept(Moditems.TOTEM_SPEED.get());
                        output.accept(Moditems.TOTEM_REGEN.get());
                        output.accept(Moditems.TOTEM_STRENGTH.get());
                        // Ingredients
                        output.accept(Moditems.NETHER_STAR_FRAGMENT.get());
                        output.accept(Moditems.DRAINED_NETHER_STAR_FRAGMENT.get());
                        output.accept(Moditems.UNSTABLE_QUARTZ.get());
                        output.accept(Moditems.INCOMPLETE_FORGED_NETHERITE.get());
                        output.accept(Moditems.PRESSURE_CORE.get());
                        output.accept(Moditems.INCOMPLETE_COPPER_PIECE.get());
                        output.accept(Moditems.FABRICATED_ELYTRA_PIECE.get());
                        output.accept(Moditems.WING.get());
                        output.accept(Moditems.SHEETED_BASE.get());
                        output.accept(Moditems.SHEETED_PILLAR.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "kinetic"),
                    Component.translatable("creativetab.beyond_limits.kinetic"),
                    0xFFFFFF,
                    List.of(ModBlocks.AROMA_DISPERSER_BLOCK.get().asItem())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "dystheism"),
                    Component.translatable("creativetab.beyond_limits.dystheism"),
                    0xFFFFFF,
                    List.of(Moditems.FRAGRANCE_CINDER.get(), Moditems.FRAGRANCE_BASTION.get(), Moditems.FRAGRANCE_MALICE.get(), Moditems.INTENSIFIED_ANCHOR.get(), Moditems.BASE_FRAGRANCE_ITEM.get(), Moditems.ANCHOR_ITEM.get())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "nature"),
                    Component.translatable("creativetab.beyond_limits.nature"),
                    0xFFFFFF,
                    List.of(Moditems.SUNFLOWER_SEEDS.get(), Moditems.SUNFLOWER_KERNAL.get(), ModBlocks.PHOSPHATE_ORE.get().asItem(), ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get().asItem(), Moditems.RAW_PHOSPHATE.get(), Moditems.SUPERPHOSPHATE.get())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "cheese"),
                    Component.translatable("creativetab.beyond_limits.cheese"),
                    0xFFFFFF,
                    List.of(Moditems.CHEESE_SLICE.get(), Moditems.BLUE_CHEESE_SLICE.get(), Moditems.PARMESAN_CHEESE_SLICE.get(), Moditems.CHEESE_BLOCK_ITEM.get(), Moditems.BLUE_CHEESE_BLOCK_ITEM.get(), Moditems.PARMESAN_CHEESE_BLOCK_ITEM.get())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "totem"),
                    Component.translatable("creativetab.beyond_limits.totem"),
                    0xFFFFFF,
                    List.of(Moditems.TOTEM_SPEED.get(), Moditems.TOTEM_REGEN.get(), Moditems.TOTEM_STRENGTH.get())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    new ResourceLocation(BeyondLimits.MOD_ID, "ingredient"),
                    Component.translatable("creativetab.beyond_limits.ingredient"),
                    0xFFFFFF,
                    List.of(Moditems.NETHER_STAR_FRAGMENT.get(), Moditems.DRAINED_NETHER_STAR_FRAGMENT.get(), Moditems.PRESSURE_CORE.get(), Moditems.FABRICATED_ELYTRA_PIECE.get(), Moditems.WING.get(), Moditems.SHEETED_BASE.get(), Moditems.SHEETED_PILLAR.get())
            ));

            TabLayout.build();
        });
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.PHOSPHATE_ORE.get());
            event.accept(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(Moditems.RAW_PHOSPHATE.get());
            event.accept(Moditems.SUPERPHOSPHATE.get());
            event.accept(Moditems.NETHER_STAR_FRAGMENT.get());
            event.accept(Moditems.DRAINED_NETHER_STAR_FRAGMENT.get());
            event.accept(Moditems.PRESSURE_CORE.get());
            event.accept(Moditems.FABRICATED_ELYTRA_PIECE.get());
            event.accept(Moditems.WING.get());
            event.accept(Moditems.SUNFLOWER_SEEDS.get());
            event.accept(Moditems.BASE_FRAGRANCE_ITEM.get());
            event.accept(Moditems.SHEETED_BASE.get());
            event.accept(Moditems.SHEETED_PILLAR.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModBlocks.CHEESE_BLOCK.get());
            event.accept(ModBlocks.BLUE_CHEESE_BLOCK.get());
            event.accept(ModBlocks.PARMESAN_CHEESE_BLOCK.get());
            event.accept(Moditems.CHEESE_SLICE.get());
            event.accept(Moditems.BLUE_CHEESE_SLICE.get());
            event.accept(Moditems.PARMESAN_CHEESE_SLICE.get());
            event.accept(Moditems.SUNFLOWER_KERNAL.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(Moditems.TOTEM_SPEED.get());
            event.accept(Moditems.TOTEM_REGEN.get());
            event.accept(Moditems.TOTEM_STRENGTH.get());
        }
    }
}