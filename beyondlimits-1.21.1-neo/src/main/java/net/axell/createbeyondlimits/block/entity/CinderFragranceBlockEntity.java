package net.axell.createbeyondlimits.block.entity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CinderFragranceBlockEntity extends BlockEntity implements IHaveGoggleInformation {

    public CinderFragranceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CINDER_FRAGRANCE.get(), pPos, pBlockState);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.literal("    ").append(Component.literal("§bFragrance: §6Cinder")));
        if (this.getPersistentData().getBoolean("SoulBound")) {
            tooltip.add(Component.literal("§7Status: §aSoul Bound"));
            tooltip.add(Component.literal("§7Active Effects:"));
            tooltip.add(Component.literal(" §6- Fire Resistance"));
            tooltip.add(Component.literal(" §e- Haste II"));
        } else {
            tooltip.add(Component.literal("§7Status: §6Unclaimed"));
        }
        return true;
    }

    // Modern 1.21.1 Registry-Aware Save Hooks
    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
    }

    // Modern 1.21.1 Network Packet Syncing
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}