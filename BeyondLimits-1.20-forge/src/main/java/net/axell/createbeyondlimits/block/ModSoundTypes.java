package net.axell.createbeyondlimits.block;

import net.axell.createbeyondlimits.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class ModSoundTypes {
    public static final SoundType AROMA = new SoundType(
            1.0f,
            1.0f,
            ModSounds.AROMA_BREAK.get(),
            ModSounds.AROMA_STEP.get(),
            ModSounds.AROMA_PLACE.get(),
            SoundEvents.STONE_HIT,
            SoundEvents.STONE_FALL
    );
}
