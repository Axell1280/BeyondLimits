package net.axell.createbeyondlimits.sound;

import net.axell.createbeyondlimits.BeyondLimits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BeyondLimits.MOD_ID);

    // Music disc sound
    public static final RegistryObject<SoundEvent> SIGNAL_MASSAGE =
            registerSoundEvent("signal_massage");

    // Custom item consume sound
    public static final RegistryObject<SoundEvent> EPIC_CONSUME =
            registerSoundEvent("epic_consume");

    //Aroma block stepping
    public static final RegistryObject<SoundEvent> AROMA_STEP =
            registerSoundEvent("aroma_step");
    public static final RegistryObject<SoundEvent> AROMA_AURA =
            registerSoundEvent("aroma_aura");
    public static final RegistryObject<SoundEvent> AROMA_PLACE =
            registerSoundEvent("aroma_place");
    public static final RegistryObject<SoundEvent> AROMA_BREAK =
            registerSoundEvent("aroma_break");
    public static final RegistryObject<SoundEvent> AROMA_CORE =
            registerSoundEvent("aroma_core");
    public static RegistryObject<SoundEvent> AROMA_AMBIENT =
            registerSoundEvent("aroma_ambient");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(
                        ResourceLocation.fromNamespaceAndPath(BeyondLimits.MOD_ID, name)
                ));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
