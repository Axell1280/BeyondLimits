package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.util.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PressureCoreItem extends Item {

    public PressureCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (level.isClientSide) return; // only on server

        if (!(entity instanceof Player player)) return;

        boolean isHeld = player.getMainHandItem().equals(stack); // main hand check
        if (!isHeld) return;

        // Apply Speed I effect while held (duration refreshed every tick)
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0, true, false, true));

        // Optional: Apply periodic direct damage using custom DamageSource every second
        int tick = player.tickCount;
        if (tick % 20 == 0) {
            // This will trigger a death message if the player dies
            player.hurt(ModDamageSources.pressureCore(level), 2.0F); // 1 heart per second
        }
    }
}
