package net.axell.createbeyondlimits.item.custom;

import org.joml.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class TotemParticles {

    // Spawn a wider, splattering spiral burst at a specific position
    public static void spawnAt(double px, double py, double pz, float r, float g, float b) {
        Level level = Minecraft.getInstance().level;
        if (level == null || !level.isClientSide) return;

        int particleCount = 75;      // fewer particles
        double maxRadius = 3.0;      // wider spread
        double maxHeight = 1.5;      // slightly taller rise

        for (int i = 0; i < particleCount; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double radius = Math.random() * maxRadius;
            double height = Math.random() * maxHeight;

            double x = px + Math.cos(angle) * radius;
            double y = py + height;
            double z = pz + Math.sin(angle) * radius;

            // Add slight randomness to motion
            double dx = (Math.cos(angle) * 0.02) + (Math.random() - 0.5) * 0.01;
            double dz = (Math.sin(angle) * 0.02) + (Math.random() - 0.5) * 0.01;
            double dy = 0.03 + Math.random() * 0.03;

            // Main colored particle
            level.addParticle(new DustParticleOptions(new Vector3f(r, g, b), 1f), x, y, z, dx, dy, dz);

            // Sparkle particles for majesty
            level.addParticle(ParticleTypes.END_ROD, x, y, z, dx / 2, dy / 2, dz / 2);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, dx / 3, dy / 3, dz / 3);
            level.addParticle(ParticleTypes.ENCHANT, x, y, z, dx / 3, dy / 4, dz / 2);
        }
    }
}
