package net.axell.createbeyondlimits.block.custom;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.axell.createbeyondlimits.block.entity.AromaDisperserBlockEntity;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.Block;

public class AromaDisperserBlock extends HorizontalKineticBlock implements IBE<AromaDisperserBlockEntity> {

    public AromaDisperserBlock(Properties properties) {
        super(properties);
        // HorizontalKineticBlock defines HORIZONTAL_FACING automatically
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        // Keeps the internal kinetic rotation axis spinning vertically (Y-axis)
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        // Power still connects exclusively from the bottom
        return face == Direction.DOWN;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Properly faces the player on placement
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof AromaDisperserBlockEntity disperser) {
            ItemStack heldItem = player.getItemInHand(hand);
            double hitX = hit.getLocation().x - pos.getX();
            int targetSlot = (hitX < 0.5) ? 0 : 1;
            ItemStack currentStored = disperser.inventory.getStackInSlot(targetSlot);

            // 1. Put potion in
            if (heldItem.getItem() instanceof PotionItem && currentStored.isEmpty()) {
                disperser.inventory.setStackInSlot(targetSlot, heldItem.split(1));
                disperser.notifyUpdate();
                return InteractionResult.SUCCESS;
            }

            // 2. Take potion out
            if (heldItem.isEmpty() && !currentStored.isEmpty()) {
                player.setItemInHand(hand, currentStored.copy());
                disperser.inventory.setStackInSlot(targetSlot, ItemStack.EMPTY);
                disperser.notifyUpdate();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public Class<AromaDisperserBlockEntity> getBlockEntityClass() {
        return AromaDisperserBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AromaDisperserBlockEntity> getBlockEntityType() {
        return ModBlockEntities.AROMA_DISPERSER.get();
    }
}