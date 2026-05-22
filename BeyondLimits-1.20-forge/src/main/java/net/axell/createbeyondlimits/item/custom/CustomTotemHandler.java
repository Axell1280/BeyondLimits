package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.networking.ModNetworking;
import net.axell.createbeyondlimits.networking.TotemActivatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CustomTotemHandler {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        ItemStack stack = entity.getMainHandItem();
        if (!(stack.getItem() instanceof CustomEffectTotemItem totem)) return;

        // Prevent death
        event.setCanceled(true);
        entity.setHealth(1F);

        // Play totem sound
        entity.level().playSound(null, entity.blockPosition(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1F, 1F);

        // Apply custom effect
        entity.addEffect(totem.getEffect());

        // Trigger particle burst for client
        if (entity instanceof Player player) {
            float[] color = totem.getParticleColor();
            TotemParticles.spawnAt(player.getX(), player.getY(), player.getZ(), color[0], color[1], color[2]);
        }

        // Send packet to server player (optional if needed)
        if (entity instanceof ServerPlayer sp) {
            ModNetworking.send(sp, new TotemActivatePacket(stack.getItem(), entity.getX(), entity.getY(), entity.getZ()));
        }

        // Consume the totem
        stack.shrink(1);
    }
}
