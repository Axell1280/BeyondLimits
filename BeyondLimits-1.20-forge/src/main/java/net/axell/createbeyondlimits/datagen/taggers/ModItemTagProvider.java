package net.axell.createbeyondlimits.datagen.taggers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;


import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, "createbeyondlimits", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // This line tells Minecraft: "Every block in the WRENCH_PICKUP_BLOCK tag
        // should also have its corresponding ITEM in the WRENCH_PICKUP_ITEM tag."
        copy(ModTags.WRENCH_PICKUP_BLOCK, ModTags.WRENCH_PICKUP_ITEM);
    }
}

