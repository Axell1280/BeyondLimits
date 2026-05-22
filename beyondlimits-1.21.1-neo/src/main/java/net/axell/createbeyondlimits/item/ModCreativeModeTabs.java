package net.axell.createbeyondlimits.item;

import com.simibubi.create.AllItems;
import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondLimits.MODID);

    // 1.21.1 ResourceLocation Syntax Fix
    public static final ResourceLocation BEYOND_LIMITS_TAB_RL =
            ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "beyond_limits_tab");

    // Replaced RegistryObject with DeferredHolder
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEYOND_LIMITS_TAB = CREATIVE_MODE_TABS.register("beyond_limits_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Moditems.BEYOND_LIMITS.get()))
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
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "kinetic"),
                    Component.translatable("creativetab.beyond_limits.kinetic"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(ModBlocks.AROMA_DISPERSER_BLOCK.get())
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "dystheism"),
                    Component.translatable("creativetab.beyond_limits.dystheism"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(Moditems.FRAGRANCE_CINDER)
                            .add(Moditems.FRAGRANCE_BASTION)
                            .add(Moditems.FRAGRANCE_MALICE)
                            .add(Moditems.INTENSIFIED_ANCHOR)
                            .add(Moditems.BASE_FRAGRANCE_ITEM)
                            .add(Moditems.ANCHOR_ITEM)
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "nature"),
                    Component.translatable("creativetab.beyond_limits.nature"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(Moditems.SUNFLOWER_SEEDS)
                            .add(Moditems.SUNFLOWER_KERNAL)
                            .add(ModBlocks.PHOSPHATE_ORE.get())
                            .add(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get())
                            .add(Moditems.RAW_PHOSPHATE)
                            .add(Moditems.SUPERPHOSPHATE)
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "cheese"),
                    Component.translatable("creativetab.beyond_limits.cheese"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(Moditems.CHEESE_SLICE)
                            .add(Moditems.BLUE_CHEESE_SLICE)
                            .add(Moditems.PARMESAN_CHEESE_SLICE)
                            .add(Moditems.CHEESE_BLOCK_ITEM)
                            .add(Moditems.BLUE_CHEESE_BLOCK_ITEM)
                            .add(Moditems.PARMESAN_CHEESE_BLOCK_ITEM)
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "totem"),
                    Component.translatable("creativetab.beyond_limits.totem"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(Moditems.TOTEM_SPEED)
                            .add(Moditems.TOTEM_REGEN)
                            .add(Moditems.TOTEM_STRENGTH)
            ));

            FancyTabSections.addSection(BEYOND_LIMITS_TAB_RL, SectionTextured.of(
                    ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "ingredient"),
                    Component.translatable("creativetab.beyond_limits.ingredient"),
                    0xFFFFFF,
                    ConglomerateOfItems.create()
                            .add(Moditems.NETHER_STAR_FRAGMENT)
                            .add(Moditems.DRAINED_NETHER_STAR_FRAGMENT)
                            .add(Moditems.PRESSURE_CORE)
                            .add(Moditems.FABRICATED_ELYTRA_PIECE)
                            .add(Moditems.WING)
                            .add(Moditems.SHEETED_BASE)
                            .add(Moditems.SHEETED_PILLAR)
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