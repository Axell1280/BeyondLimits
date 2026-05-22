package net.axell.createbeyondlimits.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
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

    private final LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> inventory);

    public AromaDisperserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AROMA_DISPERSER.get(), pos, state);
    }

    public int getDispersalRange() {
        float speed = Math.abs(getSpeed());
        // If the mechanical shaft isn't spinning, range is zero
        if (speed == 0) return 0;

        // Base 1 block radius + 1 block per 16 RPM
        int calculatedRadius = 8 + Math.round(speed / 16f);
        return Math.min(calculatedRadius, 128);
    }

    @Override
    public void tick() {
        super.tick();

        // Run only on the logical server every 20 ticks (1 second) to prevent lag
        if (level == null || level.isClientSide || level.getGameTime() % 20 != 0) return;

        int range = getDispersalRange();
        if (range <= 0) return;

        // Scan an active 3D zone around the machine
        AABB area = new AABB(worldPosition).inflate(range);
        List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class, area);

        // Check both filter item slots
        for (int i = 0; i < 2; i++) {
            ItemStack potionStack = inventory.getStackInSlot(i);
            if (potionStack.isEmpty()) continue;

            List<MobEffectInstance> effects = PotionUtils.getMobEffects(potionStack);
            if (effects.isEmpty()) continue;

            for (LivingEntity entity : entitiesInRange) {
                for (MobEffectInstance effect : effects) {
                    entity.addEffect(new MobEffectInstance(
                            effect.getEffect(),
                            100, // Duration: 5 seconds (refreshes as long as they stay in range)
                            effect.getAmplifier(),
                            true,
                            true
                    ));
                }
            }
        }
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemCap.cast();
        }
        return super.getCapability(cap, side);
    }

    public void notifyUpdate() {
        setChanged(); // Marks the block as saved/dirty for the server
        if (level != null) {
            // Sends a block update packet to all nearby clients so they see the items change
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemCap.invalidate();
    }

    @Override
    public float calculateStressApplied() {
        float impact = 4.0f; // Customize this baseline multiplier value
        this.lastStressApplied = impact;
        return impact;
    }

    // 2. Goggles information Overlay Hook
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Inherit standard Create overlay data (RPM and Stress impact stats)
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        // Add custom line computing the block radius: 8 blocks per 16 RPM -> RPM / 2
        int currentRange = (int) (Math.abs(getSpeed()) / 2f);

        tooltip.add(Component.literal("")); // Empty spacer row
        tooltip.add(Component.literal("§bAroma Range: §f" + currentRange + " blocks"));

        return true;
    }
}