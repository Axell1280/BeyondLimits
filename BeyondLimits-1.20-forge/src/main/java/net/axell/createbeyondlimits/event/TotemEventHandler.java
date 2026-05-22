package net.axell.createbeyondlimits.event;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.item.custom.CustomEffectTotemItem;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(modid = BeyondLimits.MOD_ID)
public class TotemEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;

        // Only activate when the damage would kill the player
        if (event.getAmount() < player.getHealth()) return;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        ItemStack used = ItemStack.EMPTY;

        if (main.getItem() instanceof CustomEffectTotemItem) used = main;
        else if (off.getItem() instanceof CustomEffectTotemItem) used = off;

        if (!used.isEmpty()) {
            CustomEffectTotemItem item = (CustomEffectTotemItem) used.getItem();

            event.setCanceled(true);
            player.setHealth(1.0F);

            item.activate(player, used);

            used.shrink(1); // consume the item
        }
    }
}
