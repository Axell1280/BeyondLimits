package net.axell.createbeyondlimits.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.axell.createbeyondlimits.networking.ModNetworking;
import net.axell.createbeyondlimits.networking.TotemActivatePacket;
import net.minecraft.world.entity.item.ItemEntity;

public abstract class CustomEffectTotemItem extends Item {

    public CustomEffectTotemItem(Properties properties) {
        super(properties);
    }

    // Each subclass defines its effect
    public abstract MobEffectInstance getEffect();

    // Each subclass defines particle color
    public abstract float[] getParticleColor();

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // enchanted glint
    }

    // When dropped: orbiting particles
    public boolean onEntityItemUpdate(ItemEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) {
            if (level.getGameTime() % 4 == 0) {
                double tick = level.getGameTime() / 4.0;
                double radius = 0.25;

                double x = entity.getX() + Math.cos(tick) * radius;
                double z = entity.getZ() + Math.sin(tick) * radius;
                double y = entity.getY() + 0.15;

                float[] c = getParticleColor();

                level.addParticle(
                        new net.minecraft.core.particles.DustParticleOptions(
                                new org.joml.Vector3f(c[0], c[1], c[2]),
                                1.0f
                        ),
                        x, y, z,
                        0, 0.01, 0
                );
            }
        }
        return false;
    }

    // Activate the totem (prevents death, plays sound, applies effect, triggers particles)
    public void activate(Player player, ItemStack stack) {
        Level level = player.level();

        // Play vanilla totem sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1f, 1f);

        // Apply effect
        player.addEffect(getEffect());

        // Send packet to spawn particle burst on client
        if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
            ModNetworking.send(sp, new TotemActivatePacket(this, player.getX(), player.getY(), player.getZ()));
        }

        // Consume the totem
        stack.shrink(1);
    }
}
