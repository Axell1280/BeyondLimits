package net.axell.createbeyondlimits.item.custom;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

@Mod.EventBusSubscriber
public class DroppedTotemParticles {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level; // <-- field, not method
        if (level.isClientSide) {
            AABB box = new AABB(-1000, -1000, -1000, 1000, 256, 1000);
            for (ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, box)) {
                ItemStack stack = entity.getItem();
                if (!(stack.getItem() instanceof CustomEffectTotemItem totem)) continue;

                if (level.getGameTime() % 4 != 0) continue; // reduce lag

                double tick = level.getGameTime() / 4.0;
                double radius = 0.25;

                double x = entity.getX() + Math.cos(tick) * radius;
                double z = entity.getZ() + Math.sin(tick) * radius;
                double y = entity.getY() + 0.15;

                float[] c = totem.getParticleColor();

                level.addParticle(
                        new net.minecraft.core.particles.DustParticleOptions(
                                new org.joml.Vector3f(c[0], c[1], c[2]),
                                1.0f
                        ),
                        x, y, z,
                        0, 0.01, 0
                );
            }
        }
    }
}
