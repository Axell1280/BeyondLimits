package net.axell.createbeyondlimits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.axell.createbeyondlimits.block.entity.AromaDisperserBlockEntity;
import net.axell.createbeyondlimits.client.CBLPartialModels;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class AromaDisperserRenderer implements BlockEntityRenderer<AromaDisperserBlockEntity> {

    private static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");

    public AromaDisperserRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(AromaDisperserBlockEntity be, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        float speed = be.getSpeed();
        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float angle = (speed != 0) ? (time * speed * 0.05f) % 360f : 0f;

        // Renders Kinetic Visuals
        renderModel(CBLPartialModels.AROMA_DISPERSER_AXIS, be, poseStack, buffer, combinedLight, combinedOverlay, 0.0f, angle);
        renderModel(CBLPartialModels.AROMA_DISPERSER_COG, be, poseStack, buffer, combinedLight, combinedOverlay, 0.0f, angle);
        renderModel(CBLPartialModels.AROMA_DISPERSER_CORE, be, poseStack, buffer, combinedLight, combinedOverlay, 0.78125f, -angle);

        if (speed != 0 && be.getLevel() != null) {
            // 0xAA66CCFF represents the color in hex format (Alpha, Red, Green, Blue)
            // 0xAA is the alpha (transparency), 66 is red, CC is green, FF is blue
            int color = 0xAA66CCFF;

            BeaconRenderer.renderBeaconBeam(
                    poseStack,
                    buffer,
                    BEAM_LOCATION,
                    partialTicks,
                    1.0f,               // textureScale
                    be.getLevel().getGameTime(),
                    0,                  // yOffset
                    256,                // height
                    color,              // Your packed int color
                    0.15f,              // beamRadius
                    0.2f                // glowRadius
            );
        }

        renderItems(be, poseStack, buffer, combinedLight, combinedOverlay);
    }

    private void renderModel(PartialModel model, AromaDisperserBlockEntity be, PoseStack ps, MultiBufferSource buf, int light, int overlay, float yOff, float rot) {
        ps.pushPose();
        ps.translate(0.5f, yOff, 0.5f);
        ps.mulPose(Axis.YP.rotationDegrees(rot));
        ps.translate(-0.5f, -yOff, -0.5f);

        CachedBuffers.partial(model, be.getBlockState())
                .light(light)
                .overlay(overlay)
                .renderInto(ps, buf.getBuffer(RenderType.cutout()));
        ps.popPose();
    }

    private void renderItems(AromaDisperserBlockEntity be, PoseStack ps, MultiBufferSource buf, int light, int overlay) {
        Direction facing = be.getBlockState().hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING)
                ? be.getBlockState().getValue(HorizontalKineticBlock.HORIZONTAL_FACING) : Direction.NORTH;

        float rot = switch (facing) {
            case EAST -> 90f;
            case SOUTH -> 180f;
            case WEST -> 270f;
            default -> 0f;
        };

        ps.pushPose();
        ps.translate(0.5f, 0.5f, 0.5f);
        ps.mulPose(Axis.YP.rotationDegrees(-rot));
        ps.translate(-0.5f, -0.5f, -0.5f);

        renderSlot(be.inventory.getStackInSlot(0), 0.3125f, ps, buf, light, overlay);
        renderSlot(be.inventory.getStackInSlot(1), 0.6875f, ps, buf, light, overlay);

        ps.popPose();
    }

    private void renderSlot(ItemStack stack, float xOffset, PoseStack ps, MultiBufferSource buf, int light, int overlay) {
        if (stack.isEmpty()) return;
        ps.pushPose();
        ps.translate(xOffset, 0.125f, -0.005f);
        ps.scale(0.25f, 0.25f, 0.25f);
        ps.mulPose(Axis.YP.rotationDegrees(180f));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, light, overlay, ps, buf, null, 0
        );

        ps.popPose();
    }
}