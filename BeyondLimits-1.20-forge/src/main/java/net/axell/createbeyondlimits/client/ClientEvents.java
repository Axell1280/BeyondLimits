package net.axell.createbeyondlimits.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void renderGui(RenderGuiEvent.Post event) {
        TotemAnimationRenderer.render(event.getGuiGraphics(), event.getPartialTick());

    }

    @SubscribeEvent
    public static void onRegisterModels(ModelEvent.RegisterAdditional event) {
        CBLPartialModels.init(); // class loads → static fields call PartialModel.of()
    }
}

