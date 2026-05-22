package net.axell.createbeyondlimits.block.custom;

import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.axell.createbeyondlimits.world.SoulLinkData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MaliceFragranceBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private final String myType = "malice";

    public MaliceFragranceBlock(Properties pProperties) { super(pProperties); }

    @Override public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) { return SHAPE; }
    @Override public RenderShape getRenderShape(BlockState pState) { return RenderShape.MODEL; }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND && pLevel instanceof ServerLevel serverLevel) {
            SoulLinkData data = SoulLinkData.get(serverLevel);
            UUID uuid = pPlayer.getUUID();

            if (data.isPlayerBoundToType(uuid, myType) && !pPos.equals(data.getBoundPosForType(uuid, myType))) {
                pPlayer.displayClientMessage(Component.literal("Over-binding " + myType + " is fatal!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), true);
                pPlayer.hurt(pLevel.damageSources().magic(), 4.0f);
                return InteractionResult.FAIL;
            }

            if (!data.isPlayerBoundToType(uuid, myType)) {
                data.addLink(uuid, myType, pPos);
                updateBindingState(pLevel, pPos, pState, true);
                pPlayer.displayClientMessage(Component.literal("Soul bound to Malice Fragrance.").withStyle(ChatFormatting.AQUA), false);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void updateBindingState(Level level, BlockPos pos, BlockState state, boolean bound) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null) {
            be.getPersistentData().putBoolean("SoulBound", bound);
            be.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pLevel.isClientSide && !pState.is(pNewState.getBlock()) && pLevel instanceof ServerLevel serverLevel) {
            SoulLinkData.get(serverLevel).removeLinkAt(pPos);
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pPlacer instanceof Player player && pLevel instanceof ServerLevel serverLevel) {
            if (pLevel.dimension() != Level.NETHER) {
                pLevel.destroyBlock(pPos, true);
                return;
            }
            SoulLinkData data = SoulLinkData.get(serverLevel);
            if (!data.isPlayerBoundToType(player.getUUID(), myType)) {
                data.addLink(player.getUUID(), myType, pPos);
                updateBindingState(pLevel, pPos, pState, true);
            }
        }
    }

    @Nullable @Override public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.MALICE_FRAGRANCE.get().create(pPos, pState);
    }
}