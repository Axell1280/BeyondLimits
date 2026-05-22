package net.axell.createbeyondlimits.block.custom;

import com.mojang.serialization.MapCodec;
import net.axell.createbeyondlimits.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DomesticatedSunflowerBlock extends CropBlock {
    // 1.21.1 Serialization Codec Requirement
    public static final MapCodec<DomesticatedSunflowerBlock> CODEC = simpleCodec(DomesticatedSunflowerBlock::new);

    public DomesticatedSunflowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return BlockStateProperties.AGE_3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.COARSE_DIRT) || state.is(Blocks.FARMLAND);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        if (!level.canSeeSky(above)) {
            return false;
        }
        if (!level.getBlockState(above).isAir()) {
            return false;
        }
        // Fixed 1.21.1 Light Tracking pipeline syntax
        return level.getBrightness(LightLayer.SKY, above) >= 9
                && super.canSurvive(state, level, pos);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return Moditems.SUNFLOWER_SEEDS.get();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(Moditems.SUNFLOWER_SEEDS.get());
    }
}