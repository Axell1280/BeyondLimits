package net.axell.createbeyondlimits.networking;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

// Automated registry tracking mapping to your MOD event bus
@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void registerNetworks(final RegisterPayloadHandlersEvent event) {
        // Sets up your custom namespace channel with clean automated versioning
        final PayloadRegistrar registrar = event.registrar(BeyondLimits.MODID)
                .versioned("1.0.0");

        // Registers your Totem payload using its static internal TYPE and STREAM_CODEC configurations
        registrar.playToClient(
                TotemActivatePacket.TYPE,
                TotemActivatePacket.STREAM_CODEC,
                TotemActivatePacket::handleClient
        );
    }

    /**
     * Optional utility helper method to keep your custom code clean.
     * Maps to the static method pipeline discovered in your version of NeoForge.
     */
    public static void send(ServerPlayer player, TotemActivatePacket msg) {
        PacketDistributor.sendToPlayer(player, msg);
    }
}