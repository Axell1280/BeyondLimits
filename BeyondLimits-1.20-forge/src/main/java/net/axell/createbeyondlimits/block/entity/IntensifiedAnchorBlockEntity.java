package net.axell.createbeyondlimits.block.entity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import net.axell.createbeyondlimits.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class IntensifiedAnchorBlockEntity extends BlockEntity implements IHaveGoggleInformation {

    public IntensifiedAnchorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        // Ensure you have registered INTENSIFIED_ANCHOR in ModBlockEntities
        super(ModBlockEntities.INTENSIFIED_ANCHOR.get(), pPos, pBlockState);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Header for the Zenith-tier block
        tooltip.add(Component.literal("    ").append(Component.literal("§bFragrance : §6Intensified Anchor")));

        if (this.getPersistentData().getBoolean("SoulBound")) {
            tooltip.add(Component.literal("§7Status: §5Soul Anchored"));
            tooltip.add(Component.literal("§7Active Buffs:"));
            // 0.8.0 Navigation & Passive Effects
            tooltip.add(Component.literal(" §6- Lava Navigation"));
            tooltip.add(Component.literal(" §e- Ghast Passivity"));
            tooltip.add(Component.literal(" §d- Wither Immunity"));
            tooltip.add(Component.literal(" §a- Regeneration II"));
            tooltip.add(Component.literal(" §9- Resistance II"));
        } else {
            tooltip.add(Component.literal("§7Status: §6Unclaimed")); // Hinting at the ritual
        }
        return true;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}