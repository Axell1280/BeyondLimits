package net.axell.createbeyondlimits.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseBlock extends CakeBlock {
    // 1.21.1 Codec Handling for a Block with custom parameter parameters
    public static final MapCodec<CheeseBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    com.mojang.serialization.Codec.STRING.xmap(
                            name -> java.util.Arrays.stream(CheeseType.values()).filter(t -> t.getName().equals(name)).findFirst().orElse(CheeseType.NORMAL),
                            CheeseType::getName
                    ).fieldOf("cheese_type").forGetter(block -> block.cheeseType)
            ).apply(instance, CheeseBlock::new)
    );

    private final CheeseType cheeseType;

    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D),  // 0 Bites
            Block.box(3.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D),  // 1 Bite
            Block.box(5.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D),  // 2 Bites
            Block.box(7.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D),  // 3 Bites
            Block.box(9.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D),  // 4 Bites
            Block.box(11.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D), // 5 Bites
            Block.box(13.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D)  // 6 Bites
    };

    public CheeseBlock(Properties properties, CheeseType cheeseType) {
        super(properties);
        this.cheeseType = cheeseType;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    // Fixed covariant return type signature to resolve CakeBlock definition clash
    @Override
    public MapCodec<CakeBlock> codec() {
        return (MapCodec<CakeBlock>) (Object) CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_BITE[state.getValue(BITES)];
    }

    // Modernized activation endpoint mapping
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            if (eatCheese(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        return eatCheese(level, pos, state, player);
    }

    protected InteractionResult eatCheese(Level level, BlockPos pos, BlockState state, Player player) {
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 0.25f, 1.0f);

        player.getFoodData().eat(2, 0.1F);
        applyCheeseEffects(player, cheeseType);

        int bites = state.getValue(BITES);
        if (bites < 6) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        return InteractionResult.SUCCESS;
    }

    private void applyCheeseEffects(Player player, CheeseType type) {
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 200, type.getSaturationLevel() - 1, false, true));

        if (type.getResistanceLevel() > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, type.getResistanceLevel() - 1, false, true));
        }
    }
}