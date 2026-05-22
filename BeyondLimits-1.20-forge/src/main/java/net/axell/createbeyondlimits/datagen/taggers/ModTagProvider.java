package net.axell.createbeyondlimits.datagen.taggers;

import net.axell.createbeyondlimits.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends BlockTagsProvider {
    public ModTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "createbeyondlimits", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.WRENCH_PICKUP_BLOCK)
                //.add(CustomBlocksClass.BLOCK.get());
                .add(ModBlocks.FRAGRANCE_MALICE.get())
                .add(ModBlocks.FRAGRANCE_BASTION.get())
                .add(ModBlocks.FRAGRANCE_CINDER.get())
                .add(ModBlocks.BASE_FRAGRANCE.get())
                .add(ModBlocks.ANCHOR.get())
                .add(ModBlocks.AROMA_DISPERSER_BLOCK.get());

        tag(ModTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.FRAGRANCE_MALICE.get())
                .add(ModBlocks.FRAGRANCE_BASTION.get())
                .add(ModBlocks.FRAGRANCE_CINDER.get())
                .add(ModBlocks.PHOSPHATE_ORE.get())
                .add(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get())
                .add(ModBlocks.BASE_FRAGRANCE.get())
                .add(ModBlocks.ANCHOR.get())
                .add(ModBlocks.AROMA_DISPERSER_BLOCK.get());


        tag(ModTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.FRAGRANCE_MALICE.get())
                .add(ModBlocks.FRAGRANCE_BASTION.get())
                .add(ModBlocks.FRAGRANCE_CINDER.get())
                .add(ModBlocks.DEEPSLATE_PHOSPHATE_ORE.get())
                .add(ModBlocks.AROMA_DISPERSER_BLOCK.get());


        tag(ModTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.PHOSPHATE_ORE.get())
                .add(ModBlocks.BASE_FRAGRANCE.get())
                .add(ModBlocks.ANCHOR.get());

    }
}

