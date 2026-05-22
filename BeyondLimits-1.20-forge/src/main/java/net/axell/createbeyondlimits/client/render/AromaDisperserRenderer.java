package net.axell.createbeyondlimits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.axell.createbeyondlimits.block.entity.AromaDisperserBlockEntity;
import net.axell.createbeyondlimits.client.CBLPartialModels;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock; // Added for direction property
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.core.Direction; // Added for Direction enum
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class AromaDisperserRenderer implements BlockEntityRenderer<AromaDisperserBlockEntity> {

    private static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");

    public AromaDisperserRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(
            AromaDisperserBlockEntity be,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int combinedLight,
            int combinedOverlay
    ) {
        float speed = be.getSpeed();
        float time  = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = (speed != 0) ? (time * speed * 0.05f) % 360f : 0f;

        // =====================================================================
        // KINETIC VISUALS
        // =====================================================================

        // 1. AXIS COLUMN
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.0f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5f, 0.0f, -0.5f);

        SuperByteBuffer axisBuffer = CachedBuffers.partial(CBLPartialModels.AROMA_DISPERSER_AXIS, be.getBlockState());
        axisBuffer.light(combinedLight).overlay(combinedOverlay).renderInto(poseStack, buffer.getBuffer(RenderType.cutout()));
        poseStack.popPose();

        // 2. GEAR ASSEMBLY
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.0f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5f, 0.0f, -0.5f);

        SuperByteBuffer cogBuffer = CachedBuffers.partial(CBLPartialModels.AROMA_DISPERSER_COG, be.getBlockState());
        cogBuffer.light(combinedLight).overlay(combinedOverlay).renderInto(poseStack, buffer.getBuffer(RenderType.cutout()));
        poseStack.popPose();

        // 3. BEACON CORE
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.78125f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-angle));
        poseStack.translate(-0.5f, -0.78125f, -0.5f);

        SuperByteBuffer coreBuffer = CachedBuffers.partial(CBLPartialModels.AROMA_DISPERSER_CORE, be.getBlockState());
        coreBuffer.light(combinedLight).overlay(combinedOverlay).renderInto(poseStack, buffer.getBuffer(RenderType.cutout()));
        poseStack.popPose();

        // =====================================================================
        // BEACON BEAM EFFECTS
        // =====================================================================
        if (speed != 0 && be.getLevel() != null) {
            long levelTime = be.getLevel().getGameTime();

            BeaconRenderer.renderBeaconBeam(
                    poseStack, buffer, BEAM_LOCATION, partialTicks, 1.0f, levelTime,
                    0, 256,
                    new float[]{0.4f, 0.8f, 1.0f},
                    0.15f, 0.2f
            );
        }

        // =====================================================================
        // FILTER SLOT ITEMS (Now respects block rotation!)
        // =====================================================================
        // Safe check to grab the current facing property from the blockstate
        Direction facing = be.getBlockState().hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING)
                ? be.getBlockState().getValue(HorizontalKineticBlock.HORIZONTAL_FACING)
                : Direction.NORTH;

        // Map the direction to match your blockstate JSON angles exactly
        float itemRotation = switch (facing) {
            case EAST -> 90f;
            case SOUTH -> 180f;
            case WEST -> 270f;
            default -> 0f; // NORTH
        };

        poseStack.pushPose();

        // Step A: Move system to the center of the block space
        poseStack.translate(0.5f, 0.5f, 0.5f);
        // Step B: Rotate item grid space matching the clockwise blockstate JSON layout
        poseStack.mulPose(Axis.YP.rotationDegrees(-itemRotation));
        // Step C: Move coordinate grid back outward
        poseStack.translate(-0.5f, -0.5f, -0.5f);

        // Slot 1 (Left Side relative to North face)
        ItemStack slot1 = be.inventory.getStackInSlot(0);
        if (!slot1.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.3125f, 0.125f, -0.005f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    slot1, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, be.getLevel(), 0
            );
            poseStack.popPose();
        }

        // Slot 2 (Right Side relative to North face)
        ItemStack slot2 = be.inventory.getStackInSlot(1);
        if (!slot2.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.6875f, 0.125f, -0.005f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    slot2, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, be.getLevel(), 0
            );
            poseStack.popPose();
        }

        // Pop the master horizontal rotation pose out of the stack
        poseStack.popPose();
    }
}