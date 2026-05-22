package net.axell.createbeyondlimits.item.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class SuperphosphateItem extends BoneMealItem {
    public SuperphosphateItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        
        if (!itemStack.isEmpty()) {
            InteractionResult result = super.useOn(context);
            
            if (context.getLevel() != null && context.getLevel().random.nextDouble() < 0.5) {
                super.useOn(context);
            }
            
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                itemStack.shrink(1);
            }
            
            return result.consumesAction() ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        
        return InteractionResult.PASS;
    }
}
