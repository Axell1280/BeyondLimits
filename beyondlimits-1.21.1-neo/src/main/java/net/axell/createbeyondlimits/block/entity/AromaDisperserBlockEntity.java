package net.axell.createbeyondlimits.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AromaDisperserBlockEntity extends KineticBlockEntity {

    // 2-slot internal inventory handler for the filter squares
    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() instanceof PotionItem;
        }

        @Override
        protected void onContentsChanged(int slot) {
            notifyUpdate(); // Crucial for alerting the 3D Item Renderer to update live
        }
    };

    public AromaDisperserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AROMA_DISPERSER.get(), pos, state);
    }

    public int getDispersalRange() {
        float speed = Math.abs(getSpeed());
        if (speed == 0) return 0;

        int calculatedRadius = 8 + Math.round(speed / 16f);
        return Math.min(calculatedRadius, 128);
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide || level.getGameTime() % 20 != 0) return;

        int range = getDispersalRange();
        if (range <= 0) return;

        AABB area = new AABB(worldPosition).inflate(range);
        List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class, area);

        for (int i = 0; i < 2; i++) {
            ItemStack potionStack = inventory.getStackInSlot(i);
            if (potionStack.isEmpty()) continue;

            // FIX 1: PotionContents uses get(DataComponents.POTION_CONTENTS)
            PotionContents contents = potionStack.get(DataComponents.POTION_CONTENTS);
            if (contents == null) continue;

            // FIX 2: Swapped allEffects() to getAllEffects() to fix the method resolution error
            Iterable<MobEffectInstance> effects = contents.getAllEffects();

            for (LivingEntity entity : entitiesInRange) {
                for (MobEffectInstance effect : effects) {
                    Holder<MobEffect> effectHolder = effect.getEffect();

                    entity.addEffect(new MobEffectInstance(
                            effectHolder,
                            100, // 5 seconds
                            effect.getAmplifier(),
                            true,
                            true
                    ));
                }
            }
        }
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (compound.contains("Inventory")) {
            inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        }
    }

    public void notifyUpdate() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public float calculateStressApplied() {
        float impact = 4.0f;
        this.lastStressApplied = impact;
        return impact;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        int currentRange = getDispersalRange();

        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§bAroma Range: §f" + currentRange + " blocks"));

        return true;
    }
}