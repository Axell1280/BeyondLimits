package net.axell.createbeyondlimits.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
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
    private final CheeseType cheeseType;

    // These shapes represent the block getting smaller from west to east, just like a Cake
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
        // Default state must be 0 bites
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // This is what makes the block look "sliced" in-game
        return SHAPE_BY_BITE[state.getValue(BITES)];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        // Check if the player can actually eat (is hungry or has saturation effects)
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            if (eat(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        return eat(level, pos, state, player);
    }

    protected InteractionResult eat(Level level, BlockPos pos, BlockState state, Player player) {
        // Play eating sound
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 0.25f, 1.0f);

        // Restore Hunger (2 points = 1 drumstick)
        player.getFoodData().eat(2, 0.1F);

        // Apply your custom CheeseType effects
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
        // Saturation effect
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 200, type.getSaturationLevel() - 1, false, true));

        // Resistance effect
        if (type.getResistanceLevel() > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, type.getResistanceLevel() - 1, false, true));
        }
    }
}