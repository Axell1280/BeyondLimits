package net.axell.createbeyondlimits.world;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulLinkData extends SavedData {
    // Structure: Player UUID -> Map of (Type -> Position)
    private final Map<UUID, Map<String, BlockPos>> playerLinks = new HashMap<>();

    public SoulLinkData() {}

    public static SoulLinkData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(SoulLinkData::load, SoulLinkData::new, "soullinkdata");
    }

    public boolean isPlayerBoundToType(UUID uuid, String type) {
        return playerLinks.containsKey(uuid) && playerLinks.get(uuid).containsKey(type);
    }

    public BlockPos getBoundPosForType(UUID uuid, String type) {
        if (!playerLinks.containsKey(uuid)) return null;
        return playerLinks.get(uuid).get(type);
    }

    public void addLink(UUID uuid, String type, BlockPos pos) {
        playerLinks.computeIfAbsent(uuid, k -> new HashMap<>()).put(type, pos);
        this.setDirty();
    }

    public void removeLink(UUID uuid, String type) {
        if (playerLinks.containsKey(uuid)) {
            playerLinks.get(uuid).remove(type);
            this.setDirty();
        }
    }

    public void removeLinkAt(BlockPos pos) {
        boolean changed = false;
        for (Map<String, BlockPos> typeMap : playerLinks.values()) {
            if (typeMap.values().removeIf(p -> p.equals(pos))) {
                changed = true;
            }
        }
        if (changed) this.setDirty();
    }

    public Map<String, BlockPos> getAllLinks(UUID uuid) {
        return playerLinks.getOrDefault(uuid, new HashMap<>());
    }

    public static SoulLinkData load(CompoundTag nbt) {
        SoulLinkData data = new SoulLinkData();
        ListTag playerList = nbt.getList("PlayerLinks", Tag.TAG_COMPOUND);
        for (int i = 0; i < playerList.size(); i++) {
            CompoundTag playerEntry = playerList.getCompound(i);
            UUID uuid = playerEntry.getUUID("UUID");
            Map<String, BlockPos> typeMap = new HashMap<>();

            ListTag linkList = playerEntry.getList("Links", Tag.TAG_COMPOUND);
            for (int j = 0; j < linkList.size(); j++) {
                CompoundTag linkEntry = linkList.getCompound(j);
                typeMap.put(linkEntry.getString("Type"), BlockPos.of(linkEntry.getLong("Pos")));
            }
            data.playerLinks.put(uuid, typeMap);
        }
        return data;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag nbt) {
        ListTag playerList = new ListTag();
        for (Map.Entry<UUID, Map<String, BlockPos>> playerEntry : playerLinks.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("UUID", playerEntry.getKey());

            ListTag linkList = new ListTag();
            for (Map.Entry<String, BlockPos> linkEntry : playerEntry.getValue().entrySet()) {
                CompoundTag linkTag = new CompoundTag();
                linkTag.putString("Type", linkEntry.getKey());
                linkTag.putLong("Pos", linkEntry.getValue().asLong());
                linkList.add(linkTag);
            }
            playerTag.put("Links", linkList);
            playerList.add(playerTag);
        }
        nbt.put("PlayerLinks", playerList);
        return nbt;
    }
}