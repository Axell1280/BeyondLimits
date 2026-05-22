package net.axell.createbeyondlimits.client;

import com.mojang.logging.LogUtils;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CBLPartialModels {

    // Custom partial models for the Aroma Disperser
    // Note: In newer versions, the path is relative to your block model directory automatically
    public static final PartialModel
            AROMA_DISPERSER_COG  = block("aroma/aroma_disperser_cog"),
            AROMA_DISPERSER_AXIS = block("aroma/aroma_disperser_axis"),
            AROMA_DISPERSER_CORE = block("aroma/aroma_disperser_core");

    /**
     * Call this inside your client setup event class (e.g., your FMLClientSetupEvent)
     * to force class loading and proper registration during the baking phase.
     */
    public static void init() {
        LOGGER.info("[CBL] Initializing and registering custom partial models...");
    }

    private static PartialModel block(String path) {
        // Direct ResourceLocation definition pointing to assets/createbeyondlimits/models/block/...
        return  PartialModel.of(
                ResourceLocation.fromNamespaceAndPath(
                        "createbeyondlimits",
                        "block/" + path)
        );
    }

    private static final Logger LOGGER = LogUtils.getLogger();
}