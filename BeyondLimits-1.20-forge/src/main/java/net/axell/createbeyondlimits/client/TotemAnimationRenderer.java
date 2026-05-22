package net.axell.createbeyondlimits.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TotemAnimationRenderer {

    private static boolean active = false;
    private static int ticks = 0;
    private static Item animItem;

    public static void start(Item item) {
        animItem = item;
        ticks = 0;
        active = true;
    }

    public static void render(GuiGraphics gfx, float partialTick) {
        if (!active) return;

        ticks++;

        Minecraft mc = Minecraft.getInstance();
        float progress = ticks / 20F; // 1 second

        float alpha = 1F;
        if (progress < 0.2F) alpha = progress / 0.2F;
        if (progress > 0.8F) alpha = (1F - progress) / 0.2F;

        int cx = gfx.guiWidth() / 2;
        int cy = gfx.guiHeight() / 2;

        PoseStack ps = gfx.pose();
        ps.pushPose();
        ps.translate(cx - 8, cy - 8, 0);
        ps.scale(3F, 3F, 3F);

        gfx.renderItem(new ItemStack(animItem), 0, 0);

        ps.popPose();

        spawnStar(mc, progress);

        if (ticks >= 20) active = false;
    }

    private static void spawnStar(Minecraft mc, float progress) {
        if (mc.level == null) return;

        for (int i = 0; i < 8; i++) {
            double angle = mc.level.random.nextDouble() * Math.PI * 2;
            double speed = 0.2 + progress * 0.3;

            mc.level.addParticle(
                    ParticleTypes.END_ROD,
                    mc.player.getX(),
                    mc.player.getY() + 1.0,
                    mc.player.getZ(),
                    Math.cos(angle) * speed,
                    0.05,
                    Math.sin(angle) * speed
            );
        }
    }
}
