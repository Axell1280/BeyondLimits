package net.axell.createbeyondlimits.networking;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {

    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(BeyondLimits.MOD_ID, "main"))
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .networkProtocolVersion(() -> PROTOCOL)
            .simpleChannel();



    private static int packetId = 0;

    private static int id() { return packetId++; }

    public static void register() {
        CHANNEL.messageBuilder(TotemActivatePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(TotemActivatePacket::encode)
                .decoder(TotemActivatePacket::decode)
                .consumerMainThread(TotemActivatePacket::handle)
                .add();
    }

    public static void send(ServerPlayer player, Object msg) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
