package net.axell.createbeyondlimits.datagen.loot;

import com.eliotlash.mclib.math.functions.classic.Mod;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.block.custom.DomesticatedSunflowerBlock;
import net.axell.createbeyondlimits.item.Moditems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(ModBlocks.ROSEMARY.get());
        this.dropSelf(ModBlocks.FRAGRANCE_MALICE.get());
        this.dropSelf(ModBlocks.FRAGRANCE_CINDER.get());
        this.dropSelf(ModBlocks.FRAGRANCE_BASTION.get());
        this.dropSelf(ModBlocks.INTENSIFIED_ANCHOR.get());
        this.dropSelf(ModBlocks.BASE_FRAGRANCE.get());
        this.dropSelf(ModBlocks.ANCHOR.get());
        this.dropSelf(ModBlocks.CHEESE_BLOCK.get());
        this.dropSelf(ModBlocks.BLUE_CHEESE_BLOCK.get());
        this.dropSelf(ModBlocks.PARMESAN_CHEESE_BLOCK.get());
        this.dropSelf(ModBlocks.AROMA_DISPERSER_BLOCK.get());

        this.add(ModBlocks.DOMESTIC_SUNFLOWER.get(), (block) -> {
            LootItemBlockStatePropertyCondition.Builder isMaxAge =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(DomesticatedSunflowerBlock.AGE, 3));

            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(Moditems.SUNFLOWER_SEEDS.get())
                                            .when(isMaxAge)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))),

                                    LootItem.lootTableItem(Moditems.SUNFLOWER_SEEDS.get())
                            ))
                    );
        });




        this.add(ModBlocks.PHOSPHATE_ORE.get(), (block) ->
                createSilkTouchDispatchTable(block,
                        this.applyExplosionDecay(block,
                                LootItem.lootTableItem(Moditems.RAW_PHOSPHATE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
        this.add(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get(), (block) ->
                createSilkTouchDispatchTable(block,
                        this.applyExplosionDecay(block,
                                LootItem.lootTableItem(Moditems.RAW_PHOSPHATE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))));


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}