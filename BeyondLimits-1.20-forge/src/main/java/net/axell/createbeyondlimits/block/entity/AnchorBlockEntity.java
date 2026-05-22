package net.axell.createbeyondlimits.block.entity;

import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.config.ModConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AnchorBlockEntity extends BlockEntity {
    private int ritualTimer = 0;
    private boolean isRitualActive = false;

    public AnchorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ANCHOR_BE.get(), pPos, pBlockState);
    }

    public void startRitual(Player player) {
        if (isRitualSetupValid()) {
            this.isRitualActive = true;
            this.setChanged();
            player.displayClientMessage(Component.literal("§bThe Anchor awakens... the air grows heavy."), true);
        } else {
            player.displayClientMessage(Component.literal("§cThe ritual setup is incomplete. Check the 3x3 fire and corners."), true);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AnchorBlockEntity be) {
        if (level.isClientSide || !be.isRitualActive) return;

        be.ritualTimer++;
        int mult = ModConfigs.RITUAL_PARTICLE_MULTIPLIER.get();
        int totalTicks = ModConfigs.RITUAL_DURATION_TICKS.get();

        // --- DYNAMIC PHASE SCALING ---
        // Phase 1 (Corner Floor): Starts at 20%, ends at 40%
        if (be.ritualTimer >= (totalTicks * 0.2) && be.ritualTimer <= (totalTicks * 0.4) && be.ritualTimer % 40 == 0) {
            be.consumeRitualCorners(level, pos, false); // Only eat floor
        }

        // Phase 2 (Fragrance Blocks): Starts at 60%, ends at 80%
        if (be.ritualTimer >= (totalTicks * 0.6) && be.ritualTimer <= (totalTicks * 0.8) && be.ritualTimer % 100 == 0) {
            be.consumeRitualCorners(level, pos, true); // Eat both
        }

        // --- 9x9 DOME PARTICLES ---
        if (be.ritualTimer % 5 == 0) {
            be.spawnDomeParticles((ServerLevel) level, pos, mult);
        }

        // --- REPULSION DOME ---
        if (be.ritualTimer % 5 == 0) {
            be.applyDomeRepulsion(level, pos);
        }

        // --- PARTICLE SUCKING ---
        if (be.ritualTimer % 10 == 0) {
            be.spawnSuckingParticles((ServerLevel) level, pos, mult);
        }

        // Explosions scale with timer
        if (be.ritualTimer % 1200 == 0) {
            level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3.0f, Level.ExplosionInteraction.NONE);
        }

        // Final completion check using Config
        if (be.ritualTimer >= totalTicks) {
            be.finishRitual(level, pos);
        }
    }

    private void spawnDomeParticles(ServerLevel level, BlockPos pos, int mult) {
        double radius = 4.5;
        for (int i = 0; i < 5 * mult; i++) {
            double u = level.random.nextDouble();
            double v = level.random.nextDouble();
            double theta = 2 * Math.PI * u;
            double phi = Math.acos(v);
            double x = radius * Math.cos(theta) * Math.sin(phi);
            double y = radius * Math.cos(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi);
            if (y >= 0) {
                level.sendParticles(ParticleTypes.SOUL, pos.getX() + 0.5 + x, pos.getY() + y, pos.getZ() + 0.5 + z, 1, 0, 0, 0, 0);
            }
        }
    }

    private void applyDomeRepulsion(Level level, BlockPos pos) {
        double radius = 4.5;
        level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(radius)).forEach(entity -> {
            double dx = entity.getX() - (pos.getX() + 0.5);
            double dy = entity.getY() - pos.getY();
            double dz = entity.getZ() - (pos.getZ() + 0.5);
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (dist < radius) {
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                double pushX = (dx / dist) * 0.8;
                double pushZ = (dz / dist) * 0.8;
                entity.setDeltaMovement(new Vec3(pushX, 0.3, pushZ));
                entity.hurtMarked = true;
            }
        });
    }

    private void consumeRitualCorners(Level level, BlockPos pos, boolean eatTop) {
        BlockPos[] corners = { pos.offset(-2, 0, -2), pos.offset(2, 0, -2), pos.offset(-2, 0, 2), pos.offset(2, 0, 2) };
        for (BlockPos p : corners) {
            if (eatTop && !level.getBlockState(p).isAir()) {
                consumeBlock(level, p); // Eat Fragrance
                break;
            } else if (!level.getBlockState(p.below()).isAir()) {
                consumeBlock(level, p.below()); // Eat Floor
                break;
            }
        }
    }

    private void consumeBlock(Level level, BlockPos target) {
        if (!level.getBlockState(target).isAir()) {
            level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
            if (level instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.LARGE_SMOKE, target.getX()+0.5, target.getY()+0.5, target.getZ()+0.5, 10, 0.1, 0.1, 0.1, 0.05);
                sl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, target.getX()+0.5, target.getY()+0.5, target.getZ()+0.5, 5, 0.1, 0.1, 0.1, 0.02);
            }
        }
    }

    private void spawnSuckingParticles(ServerLevel level, BlockPos pos, int multiplier) {
        BlockPos[] corners = { pos.offset(-2, 0, -2), pos.offset(2, 0, -2), pos.offset(-2, 0, 2), pos.offset(2, 0, 2) };
        for (BlockPos corner : corners) {
            double dx = (pos.getX() + 0.5) - (corner.getX() + 0.5);
            double dz = (pos.getZ() + 0.5) - (corner.getZ() + 0.5);
            int count = 5 * multiplier;
            for (int i = 0; i < count; i++) {
                double step = i / (double) count;
                level.sendParticles(ParticleTypes.SOUL, corner.getX()+0.5+(dx*step), pos.getY()+0.5, corner.getZ()+0.5+(dz*step), 1, 0, 0, 0, 0);
            }
        }
    }

    private void finishRitual(Level level, BlockPos pos) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.sqrt(x*x + z*z) <= 2.8) {
                    BlockPos target = pos.offset(x, -1, z);
                    level.setBlock(target, Blocks.BEDROCK.defaultBlockState(), 3);
                }
            }
        }
        level.players().forEach(p -> { if (p.distanceToSqr(pos.getCenter()) < 400) p.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140, 0)); });
        level.setBlock(pos, ModBlocks.INTENSIFIED_ANCHOR.get().defaultBlockState(), 3);
    }

    private boolean isRitualSetupValid() {
        if (level == null) return false;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                if (!level.getBlockState(worldPosition.offset(x, 0, z)).is(Blocks.SOUL_FIRE)) return false;
            }
        }
        BlockPos[] corners = { worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 0, -2), worldPosition.offset(-2, 0, 2), worldPosition.offset(2, 0, 2) };
        int activeFragrances = 0;
        int emptyFragrances = 0;
        Block firstType = null;
        for (BlockPos p : corners) {
            BlockState state = level.getBlockState(p);
            if (state.is(ModBlocks.BASE_FRAGRANCE.get())) {
                emptyFragrances++;
            } else if (isFragrance(state)) {
                if (firstType != null && state.is(firstType)) return false;
                firstType = state.getBlock();
                activeFragrances++;
            }
        }
        return activeFragrances == 2 && emptyFragrances == 2;
    }

    private boolean isFragrance(BlockState state) {
        return state.is(ModBlocks.FRAGRANCE_MALICE.get()) || state.is(ModBlocks.FRAGRANCE_CINDER.get()) || state.is(ModBlocks.FRAGRANCE_BASTION.get());
    }

    @Override
    public void load(CompoundTag pTag) { super.load(pTag); ritualTimer = pTag.getInt("timer"); isRitualActive = pTag.getBoolean("active"); }
    @Override
    protected void saveAdditional(CompoundTag pTag) { pTag.putInt("timer", ritualTimer); pTag.putBoolean("active", isRitualActive); super.saveAdditional(pTag); }
}