package net.axell.createbeyondlimits.client.render;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import net.axell.createbeyondlimits.block.entity.AromaDisperserBlockEntity;
import net.axell.createbeyondlimits.client.CBLPartialModels;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class AromaDisperserInstance implements BlockEntityVisual<AromaDisperserBlockEntity> {

    private final AromaDisperserBlockEntity blockEntity;
    private RotatingInstance coreModel;

    public AromaDisperserInstance(VisualizationContext context, AromaDisperserBlockEntity blockEntity, float partialTick) {
        this.blockEntity = blockEntity;

        // Converts your separate _core partial model into Flywheel's preferred format
        dev.engine_room.flywheel.api.model.Model flywheelModel =
                dev.engine_room.flywheel.lib.model.Models.partial(CBLPartialModels.AROMA_DISPERSER_CORE);

        // Tell Flywheel to handle rendering this core model dynamically on the GPU
        this.coreModel = context.instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, flywheelModel)
                .createInstance();
    }

    @Override
    public void update(float partialTick) {
        if (coreModel != null) {
            BlockState state = blockEntity.getBlockState();

            // Safe directional check matching our new HorizontalKineticBlock properties
            Direction facing = state.hasProperty(HorizontalKineticBlock.HORIZONTAL_FACING)
                    ? state.getValue(HorizontalKineticBlock.HORIZONTAL_FACING)
                    : Direction.NORTH;

            // Calculate the stationary rotation adjustment angle based on block placement
            float horizontalAngle = switch (facing) {
                case EAST -> 90f;
                case SOUTH -> 180f;
                case WEST -> 270f;
                default -> 0f; // NORTH
            };

            // 1. Run base setups to pull kinetic variables
            coreModel.setup(blockEntity);

            // 2. Invert the speed value to preserve counter-rotation logic on the GPU
            coreModel.setRotationalSpeed(-blockEntity.getSpeed());

            // 3. FIX: Safely cast the block to KineticBlock to fetch its true rotation axis
            Direction.Axis kineticAxis = Direction.Axis.Y;
            if (state.getBlock() instanceof com.simibubi.create.content.kinetics.base.KineticBlock kineticBlock) {
                kineticAxis = kineticBlock.getRotationAxis(state);
            }

            float baseOffset = blockEntity.getRotationAngleOffset(kineticAxis);

            // 4. Apply the final combined offset
            coreModel.setRotationOffset(baseOffset - horizontalAngle);

            // 5. Update its absolute position coordinates in the world
            coreModel.setPosition(blockEntity.getBlockPos());
        }
    }


    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        if (coreModel != null) {
            consumer.accept(coreModel);
        }
    }

    @Override
    public void delete() {
        if (coreModel != null) {
            coreModel.delete();
        }
    }
}