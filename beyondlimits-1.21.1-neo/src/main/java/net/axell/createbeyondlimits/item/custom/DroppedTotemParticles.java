package net.axell.createbeyondlimits.item.custom;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.joml.Vector3f;

// Updated to use the NeoForge EventBusSubscriber annotation targeting the GAME bus
@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DroppedTotemParticles {

    // 1.21.1 uses LevelTickEvent.Post or LevelTickEvent.Pre instead of a single TickEvent with phases
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        // Particle processing must run only on the client side environment
        if (level.isClientSide) {
            AABB box = new AABB(-1000, -1000, -1000, 1000, 256, 1000);

            for (ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, box)) {
                ItemStack stack = entity.getItem();
                if (!(stack.getItem() instanceof CustomEffectTotemItem totem)) continue;

                if (level.getGameTime() % 4 != 0) continue; // Performance optimizer throttle

                double tick = level.getGameTime() / 4.0;
                double radius = 0.25;

                double x = entity.getX() + Math.cos(tick) * radius;
                double z = entity.getZ() + Math.sin(tick) * radius;
                double y = entity.getY() + 0.15;

                float[] c = totem.getParticleColor();

                // Spawns vanilla redstone dust options with color modifiers
                level.addParticle(
                        new DustParticleOptions(
                                new Vector3f(c[0], c[1], c[2]),
                                1.0f
                        ),
                        x, y, z,
                        0, 0.01, 0
                );
            }
        }
    }
}