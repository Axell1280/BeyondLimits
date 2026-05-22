package net.axell.createbeyondlimits.sound;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {

    // 1.21.1 Registry Setup targeting BuiltInRegistries
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BeyondLimits.MODID);

    // Music disc sound
    public static final DeferredHolder<SoundEvent, SoundEvent> SIGNAL_MASSAGE =
            registerSoundEvent("signal_massage");

    // Custom item consume sound
    public static final DeferredHolder<SoundEvent, SoundEvent> EPIC_CONSUME =
            registerSoundEvent("epic_consume");

    // Aroma block stepping
    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_STEP =
            registerSoundEvent("aroma_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_AURA =
            registerSoundEvent("aroma_aura");

    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_PLACE =
            registerSoundEvent("aroma_place");

    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_BREAK =
            registerSoundEvent("aroma_break");

    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_CORE =
            registerSoundEvent("aroma_core");

    public static final DeferredHolder<SoundEvent, SoundEvent> AROMA_AMBIENT =
            registerSoundEvent("aroma_ambient");

    // Helper registration wrapper updated with 1.21.1 syntax layouts
    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(
                        ResourceLocation.fromNamespaceAndPath(BeyondLimits.MODID, name)
                ));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}