package net.axell.createbeyondlimits.ponder;

import net.axell.createbeyondlimits.block.ModBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BeyondLimitsPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return "createbeyondlimits";
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        // Use the Registry Key for the item
        ResourceLocation bastionId = ForgeRegistries.ITEMS.getKey(ModBlocks.FRAGRANCE_BASTION.get().asItem());
        ResourceLocation maliceId = ForgeRegistries.ITEMS.getKey(ModBlocks.FRAGRANCE_MALICE.get().asItem());
        ResourceLocation cinderId = ForgeRegistries.ITEMS.getKey(ModBlocks.FRAGRANCE_CINDER.get().asItem());
        ResourceLocation anchorId = ForgeRegistries.ITEMS.getKey(ModBlocks.ANCHOR.get().asItem());
        ResourceLocation disperserId = ForgeRegistries.ITEMS.getKey(ModBlocks.AROMA_DISPERSER_BLOCK.get().asItem());

        // This links the item to your storyboard method
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
        // Optional: Leave empty for now to avoid errors with Create's tags.
        // If you want a category in the Ponder UI, you'd define a custom ResourceLocation here.
    }
}
