package net.axell.createbeyondlimits.block.custom;

import com.simibubi.create.AllItems;
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
import net.minecraft.world.level.Explosion;
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

public class IntensifiedAnchorBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 14.0D, 21.0D, 14.0D);
    private final String myType = "anchor";

    public IntensifiedAnchorBlock(Properties pProperties) { super(pProperties); }

    @Override public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) { return SHAPE; }
    @Override public RenderShape getRenderShape(BlockState pState) { return RenderShape.MODEL; }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND && pLevel instanceof ServerLevel serverLevel) {
            SoulLinkData data = SoulLinkData.get(serverLevel);
            UUID uuid = pPlayer.getUUID();

            if (data.isPlayerBoundToType(uuid, myType) && !pPos.equals(data.getBoundPosForType(uuid, myType))) {
                pPlayer.displayClientMessage(Component.literal("Your soul is already anchored elsewhere!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), true);
                pPlayer.hurt(pLevel.damageSources().magic(), 6.0f);
                return InteractionResult.FAIL;
            }

            if (pPlayer.getItemInHand(pHand).is(AllItems.WRENCH.get()) && data.isPlayerBoundToType(uuid, myType)) {
                pPlayer.displayClientMessage(Component.literal("The Anchor is locked to this coordinate.").withStyle(ChatFormatting.DARK_PURPLE), true);
                return InteractionResult.FAIL;
            }

            if (!data.isPlayerBoundToType(uuid, myType)) {
                data.addLink(uuid, myType, pPos);
                updateBindingState(pLevel, pPos, pState, true);
                pPlayer.displayClientMessage(Component.literal("Soul have been ANCHORED.").withStyle(ChatFormatting.GOLD), false);
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

    @Override
    public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be != null && be.getPersistentData().getBoolean("SoulBound")) {
            return 0.0F;
        }
        return super.getDestroyProgress(pState, pPlayer, pLevel, pPos);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null && be.getPersistentData().getBoolean("SoulBound")) {
            return 3600000.0F;
        }
        return super.getExplosionResistance(state, level, pos, explosion);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true; // Prevents the block from creating a dark shadow inside itself
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0f; // Prevents lighting glitches on the model faces
    }

    @Nullable @Override public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.INTENSIFIED_ANCHOR.get().create(pPos, pState);
    }
}