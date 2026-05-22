package net.axell.createbeyondlimits.config;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Mechanics Category
    public static final ModConfigSpec.BooleanValue ENABLE_NATURAL_BREEDING;
    public static final ModConfigSpec.DoubleValue BREEDING_CHANCE_PERCENT;

    // Fragrance Category
    public static final ModConfigSpec.IntValue FRAGRANCE_REBIND_DAMAGE;

    // Ritual Category
    public static final ModConfigSpec.IntValue RITUAL_PARTICLE_MULTIPLIER;
    public static final ModConfigSpec.IntValue RITUAL_DURATION_TICKS;

    static {

        // --- ANIMAL MECHANICS ---
        BUILDER.push("Animal Mechanics");

        ENABLE_NATURAL_BREEDING = BUILDER
                .comment("Whether animals should randomly fall in love and breed on their own.")
                .define("enableNaturalBreeding", true);

        BREEDING_CHANCE_PERCENT = BUILDER
                .comment("The percentage chance (0.0 to 100.0) for an animal to seek a partner every 20 seconds.")
                .defineInRange("breedingChancePercent", 0.33D, 0.0D, 100.0D);

        BUILDER.pop();

        // --- FRAGRANCE SETTINGS ---
        BUILDER.push("Fragrance System");

        FRAGRANCE_REBIND_DAMAGE = BUILDER
                .comment("The amount of damage (in half-hearts) dealt when trying to bind to a new fragrance while already bound.")
                .defineInRange("rebindDamage", 2, 0, 20);

        BUILDER.pop();

        // --- RITUAL SETTINGS ---
        BUILDER.push("Anchor Ritual");

        RITUAL_PARTICLE_MULTIPLIER = BUILDER
                .comment(
                        "Multiplies the amount of particles in the Anchor ritual. Default is 1.",
                        "Increase this if your PC can handle it, or decrease it if you experience lag."
                )
                .defineInRange("particleMultiplier", 1, 1, 20);

        RITUAL_DURATION_TICKS = BUILDER
                .comment("The total length of the ritual in ticks (20 ticks = 1 second). Default is 6000 (5 minutes).")
                .defineInRange("ritualDurationTicks", 6000, 1200, 72000);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static void register() {
        ModLoadingContext.get().getActiveContainer().registerConfig(
                ModConfig.Type.COMMON,
                SPEC,
                "createbeyondlimits-common.toml"
        );
    }
}