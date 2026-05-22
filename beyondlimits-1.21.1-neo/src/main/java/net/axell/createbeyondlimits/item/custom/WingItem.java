package net.axell.createbeyondlimits.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WingItem extends Item {

    public WingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (level.isClientSide) return; // only on server

        if (!(entity instanceof Player player)) return;

        boolean isHeld = player.getMainHandItem().equals(stack); // main hand check
        if (!isHeld) return;

        // Apply Speed I effect while held (duration refreshed every tick)
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 2, true, false, true));
    }
}
