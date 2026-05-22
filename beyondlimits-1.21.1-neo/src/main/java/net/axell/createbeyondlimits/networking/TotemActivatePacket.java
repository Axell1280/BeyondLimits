package net.axell.createbeyondlimits.networking;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.item.custom.CustomEffectTotemItem;
import net.axell.createbeyondlimits.item.custom.TotemParticles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TotemActivatePacket(Item item, double x, double y, double z) implements CustomPacketPayload {

    // 1.21.1 Custom ID registration for your payload
    public static final Type<TotemActivatePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, "totem_activate")
    );

    public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, TotemActivatePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(BuiltInRegistries.ITEM.key()), TotemActivatePacket::item,
            ByteBufCodecs.DOUBLE, TotemActivatePacket::x,
            ByteBufCodecs.DOUBLE, TotemActivatePacket::y,
            ByteBufCodecs.DOUBLE, TotemActivatePacket::z,
            TotemActivatePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Modern unified client-side packet executor mapping
    public static void handleClient(final TotemActivatePacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (payload.item() instanceof CustomEffectTotemItem totem) {
                float[] c = totem.getParticleColor();
                TotemParticles.spawnAt(payload.x(), payload.y(), payload.z(), c[0], c[1], c[2]);
            }
        });
    }
}