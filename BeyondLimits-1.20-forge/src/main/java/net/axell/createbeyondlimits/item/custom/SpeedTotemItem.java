package net.axell.createbeyondlimits.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class SpeedTotemItem extends CustomEffectTotemItem {
    public SpeedTotemItem(Properties p) { super(p); }

    @Override
    public MobEffectInstance getEffect() {
        return new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1);// 20s Speed II
    }
    @Override
    public float[] getParticleColor() {
        return new float[]{0.3f, 0.7f, 1.0f};
    }

}
