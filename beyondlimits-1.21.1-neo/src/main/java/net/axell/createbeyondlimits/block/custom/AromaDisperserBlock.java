package net.axell.createbeyondlimits.block.custom;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.axell.createbeyondlimits.block.entity.AromaDisperserBlockEntity;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;

public class AromaDisperserBlock extends HorizontalKineticBlock implements IBE<AromaDisperserBlockEntity> {

    public static final MapCodec<AromaDisperserBlock> CODEC = simpleCodec(AromaDisperserBlock::new);

    public AromaDisperserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalKineticBlock> codec() {
        return CODEC;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    // 1. Handling Item Insertion (Putting Potion in)
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack heldItem,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof AromaDisperserBlockEntity disperser) {
            double hitX = hit.getLocation().x - pos.getX();
            int targetSlot = (hitX < 0.5) ? 0 : 1;
            ItemStack currentStored = disperser.inventory.getStackInSlot(targetSlot);

            if (heldItem.getItem() instanceof PotionItem && currentStored.isEmpty()) {
                disperser.inventory.setStackInSlot(targetSlot, heldItem.split(1));
                disperser.notifyUpdate();
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    // 2. FIXED FOR CREATE HIERARCHY: Swapped to InteractionResult to match HorizontalKineticBlock
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof AromaDisperserBlockEntity disperser) {
            double hitX = hit.getLocation().x - pos.getX();
            int targetSlot = (hitX < 0.5) ? 0 : 1;
            ItemStack currentStored = disperser.inventory.getStackInSlot(targetSlot);

            if (!currentStored.isEmpty()) {
                if (!player.getInventory().add(currentStored.copy())) {
                    player.drop(currentStored.copy(), false);
                }
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