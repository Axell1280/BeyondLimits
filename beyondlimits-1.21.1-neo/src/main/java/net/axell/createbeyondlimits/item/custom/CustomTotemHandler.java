package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.networking.TotemActivatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

// Updated for NeoForge Event Bus Mapping
@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CustomTotemHandler {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        ItemStack stack = entity.getMainHandItem();
        if (!(stack.getItem() instanceof CustomEffectTotemItem totem)) return;

        // Prevent death mechanics
        event.setCanceled(true);
        entity.setHealth(1.0F);

        // Clear lethal status effects before applying new ones if needed
        entity.removeAllEffects();

        // Play totem activation sound
        entity.level().playSound(null, entity.blockPosition(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

        // Apply your custom totem's specific active status effect
        entity.addEffect(totem.getEffect());

        // Trigger particle burst for the client
        if (entity instanceof Player player) {
            float[] color = totem.getParticleColor();
            TotemParticles.spawnAt(player.getX(), player.getY(), player.getZ(), color[0], color[1], color[2]);
        }

        if (entity instanceof ServerPlayer sp) {
            // Fulfills your precise PacketDistributor method structure
            PacketDistributor.sendToPlayer(
                    sp,
                    new TotemActivatePacket(stack.getItem(), entity.getX(), entity.getY(), entity.getZ())
            );
        }

        // Consume the totem structure
        stack.shrink(1);
    }
}