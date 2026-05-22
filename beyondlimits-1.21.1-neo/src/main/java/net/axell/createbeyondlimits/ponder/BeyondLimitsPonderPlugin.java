package net.axell.createbeyondlimits.ponder;

import net.axell.createbeyondlimits.block.ModBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class BeyondLimitsPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return "createbeyondlimits";
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        // Updated to use vanilla BuiltInRegistries for 1.21.1 NeoForge compatibility
        ResourceLocation bastionId = BuiltInRegistries.ITEM.getKey(ModBlocks.FRAGRANCE_BASTION.get().asItem());
        ResourceLocation maliceId = BuiltInRegistries.ITEM.getKey(ModBlocks.FRAGRANCE_MALICE.get().asItem());
        ResourceLocation cinderId = BuiltInRegistries.ITEM.getKey(ModBlocks.FRAGRANCE_CINDER.get().asItem());
        ResourceLocation anchorId = BuiltInRegistries.ITEM.getKey(ModBlocks.ANCHOR.get().asItem());
        ResourceLocation disperserId = BuiltInRegistries.ITEM.getKey(ModBlocks.AROMA_DISPERSER_BLOCK.get().asItem());

        // Linking items to storyboard animations remains clean and native
        helper.forComponents(bastionId)
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "bastion"),
                        ModPonderScenes::bastionScene
                );

        helper.forComponents(maliceId)
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "malice"),
                        ModPonderScenes::maliceScene
                );

        helper.forComponents(cinderId)
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "cinder"),
                        ModPonderScenes::cinderScene
                );

        helper.forComponents(anchorId)
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "int_anch"),
                        ModPonderScenes::anchorRitualScene
                );

        helper.forComponents(disperserId)
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "aroma_disperser"),
                        ModPonderScenes::aromaDisperserScene
                );
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        // Optional placeholder setup for custom Ponder UI category groupings
    }
}