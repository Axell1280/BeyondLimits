package net.axell.createbeyondlimits.client;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void renderGui(RenderGuiLayerEvent.Post event) {
        // BYPASS: Do not use the event object for the tracker.
        // Minecraft.getInstance() is global and has access to the active DeltaTracker.
        float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

        TotemAnimationRenderer.render(event.getGuiGraphics(), partialTick);
    }
}