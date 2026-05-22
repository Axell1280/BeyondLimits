package net.axell.createbeyondlimits.block.custom;

import net.axell.createbeyondlimits.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RosemaryBlock extends CropBlock {
    // Defining 5 stages: 0, 1, 2, 3, 4
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);

    public RosemaryBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 4;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.FARMLAND) || state.is(Blocks.PODZOL) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        // Change this if you have specific Rosemary seeds
        return Moditems.ROSEMARY.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(Moditems.ROSEMARY.get());
    }
}