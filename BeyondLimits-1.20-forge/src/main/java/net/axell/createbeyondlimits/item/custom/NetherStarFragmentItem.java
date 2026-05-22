package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.item.Moditems;
import net.axell.createbeyondlimits.sound.ModSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;

public class NetherStarFragmentItem extends Item {

    public NetherStarFragmentItem(Properties pProperties) {
        super(pProperties.durability(5)); // 5 uses
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Apply God Apple effects
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));

            // Play your custom sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.EPIC_CONSUME.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            // Add cooldown (5 seconds = 100 ticks)
            player.getCooldowns().addCooldown(this, 100);
        }

        // Damage the item
        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));

        // If broken, return Drained Nether Star Fragment
        if (stack.isEmpty()) {
            return InteractionResultHolder.success(new ItemStack(Moditems.DRAINED_NETHER_STAR_FRAGMENT.get()));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
