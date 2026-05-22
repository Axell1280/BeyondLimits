package net.axell.createbeyondlimits.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class StrengthTotemItem extends CustomEffectTotemItem {
    public StrengthTotemItem(Properties p) { super(p); }

    @Override
    public MobEffectInstance getEffect() {
        return new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1); // 20s Strength II
    }
    @Override
    public float[] getParticleColor() {
        return new float[]{1.0f, 0.2f, 0.2f};
    }

}
