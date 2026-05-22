package net.axell.createbeyondlimits.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class RegenTotemItem extends CustomEffectTotemItem {
    public RegenTotemItem(Properties p) { super(p); }

    @Override
    public MobEffectInstance getEffect() {
        return new MobEffectInstance(MobEffects.REGENERATION, 200, 1);

    }
    @Override
    public float[] getParticleColor() {
        return new float[]{1.0f, 0.3f, 0.8f};
    }

}
