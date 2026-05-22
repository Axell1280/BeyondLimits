package net.axell.createbeyondlimits.networking;

import net.axell.createbeyondlimits.item.custom.CustomEffectTotemItem;
import net.axell.createbeyondlimits.item.custom.TotemParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TotemActivatePacket {

    private final Item item;
    private final double x, y, z;

    public TotemActivatePacket(Item item, double x, double y, double z) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void encode(TotemActivatePacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(Item.getId(msg.item));
        buf.writeDouble(msg.x);
        buf.writeDouble(msg.y);
        buf.writeDouble(msg.z);
    }

    public static TotemActivatePacket decode(FriendlyByteBuf buf) {
        Item item = Item.byId(buf.readVarInt());
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new TotemActivatePacket(item, x, y, z);
    }

    public static void handle(TotemActivatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> Minecraft.getInstance().execute(() -> {
            if (msg.item instanceof CustomEffectTotemItem totem) {
                float[] c = totem.getParticleColor();
                TotemParticles.spawnAt(msg.x, msg.y, msg.z, c[0], c[1], c[2]);
            }
        }));
        ctx.get().setPacketHandled(true);
    }
}
