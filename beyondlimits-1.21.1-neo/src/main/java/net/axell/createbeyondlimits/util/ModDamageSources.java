package net.axell.createbeyondlimits.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class ModDamageSources {

    public static final ResourceKey<DamageType> PRESSURE_CORE_TYPE =
            ResourceKey.create(
                    Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath("createbeyondlimits", "pressure_core")
            );

    public static DamageSource pressureCore(Level level) {
        return new DamageSource(
                level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(PRESSURE_CORE_TYPE)
        );
    }
}
