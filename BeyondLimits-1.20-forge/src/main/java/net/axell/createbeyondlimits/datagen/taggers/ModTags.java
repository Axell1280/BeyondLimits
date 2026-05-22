package net.axell.createbeyondlimits.datagen.taggers;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class ModTags {
    public static final TagKey<Block> WRENCH_PICKUP_BLOCK = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("create", "wrench_pickup"));

    public static final TagKey<Item> WRENCH_PICKUP_ITEM = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("create", "wrench_pickup"));

    public static final TagKey<Block> MINEABLE_WITH_PICKAXE = BlockTags.MINEABLE_WITH_PICKAXE;
    public static final TagKey<Block> NEEDS_STONE_TOOL = BlockTags.NEEDS_STONE_TOOL;
    public static final TagKey<Block> NEEDS_IRON_TOOL = BlockTags.NEEDS_IRON_TOOL;
    public static final TagKey<Block> NEEDS_DIAMOND_TOOL = BlockTags.NEEDS_DIAMOND_TOOL;
}
