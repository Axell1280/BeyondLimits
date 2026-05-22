package net.axell.createbeyondlimits.event;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.item.custom.CustomEffectTotemItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TotemEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // Check if the damage would kill the player
        if (event.getAmount() < player.getHealth()) return;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        ItemStack used = ItemStack.EMPTY;

        if (main.getItem() instanceof CustomEffectTotemItem) {
            used = main;
        } else if (off.getItem() instanceof CustomEffectTotemItem) {
            used = off;
        }

        if (!used.isEmpty()) {
            CustomEffectTotemItem item = (CustomEffectTotemItem) used.getItem();

            event.setCanceled(true);
            player.setHealth(1.0F);

            item.activate(player, used);

            used.shrink(1); // consume the item
        }
    }
}