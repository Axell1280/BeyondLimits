package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.block.custom.CheeseType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CheeseSliceItem extends Item {

    private final CheeseType cheeseType;

    public CheeseSliceItem(Properties properties, CheeseType cheeseType) {
        super(properties);
        this.cheeseType = cheeseType;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {

            // Saturation (effect, not food stat)
            player.addEffect(new MobEffectInstance(
                    MobEffects.SATURATION,
                    200,
                    cheeseType.getSaturationLevel() - 1,
                    false,
                    true
            ));

            // Resistance if applicable
            if (cheeseType.getResistanceLevel() > 0) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.DAMAGE_RESISTANCE,
                        200,
                        cheeseType.getResistanceLevel() - 1,
                        false,
                        true
                ));
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }
}
