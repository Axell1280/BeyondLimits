package net.axell.createbeyondlimits.block.custom;

import net.axell.createbeyondlimits.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class DomesticatedSunflowerBlock extends CropBlock {

    public DomesticatedSunflowerBlock(Properties properties) {
        super(properties);
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        if (!level.canSeeSky(above)) {
            return false;
        }
        if (!level.getBlockState(above).isAir()) {
            return false;
        }
        return level.getBrightness(LightLayer.SKY, above) >= 9
                && super.canSurvive(state, level, pos);
    }


    @Override
    protected ItemLike getBaseSeedId() {
        return Moditems.SUNFLOWER_SEEDS.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(Moditems.SUNFLOWER_SEEDS.get());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (this.isMaxAge(state)) {
            RandomSource random = builder.getLevel().getRandom();
            int count = 1 + random.nextInt(4); // 1–4 seeds
            return List.of(new ItemStack(Moditems.SUNFLOWER_SEEDS.get(), count));
        } else {
            return List.of(new ItemStack(Moditems.SUNFLOWER_SEEDS.get(), 1));
        }
    }
}