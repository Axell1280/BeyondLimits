package net.axell.createbeyondlimits.advancement;

import net.axell.createbeyondlimits.BeyondLimits; // your mod's main class / MOD_ID
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

import java.util.function.Supplier;

public class ModCriteria {
    public static final DeferredRegister<net.minecraft.advancements.CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, BeyondLimits.MODID);

    public static final Supplier<RitualStartedTrigger> RITUAL_STARTED =
            TRIGGERS.register("ritual_started", RitualStartedTrigger::new);

    public static final Supplier<AnchorClaimedTrigger> ANCHOR_CLAIMED =
            TRIGGERS.register("anchor_claimed", AnchorClaimedTrigger::new);

    public static void register(IEventBus modEventBus) {
        TRIGGERS.register(modEventBus);
    }
}