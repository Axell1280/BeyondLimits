package net.axell.createbeyondlimits.ponder;

import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

public class ModPonderScenes {

    public static void anchorRitualScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("anchor_ritual", "The Intensified Anchor Ritual");
        scene.configureBasePlate(0, 0, 9);

        BlockPos anchorPos = new BlockPos(4, 1, 4); // Center of your NBT
        // The 3x3 Soul Fire Ring from your NBT
        Selection fireRing = util.select().fromTo(3, 1, 3, 5, 1, 5).substract(util.select().position(anchorPos));

        // 1. Show Base and Anchor
        scene.addKeyframe();
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);
        scene.world().showSection(util.select().position(anchorPos), Direction.DOWN);
        scene.idle(15);
        scene.overlay().showText(20).text("Place the Anchor at the center of your platform.")
                .pointAt(util.vector().blockSurface(anchorPos, Direction.UP));
        scene.idle(20);

        // 2. Show the Fire Ring
        scene.addKeyframe();
        scene.world().showSection(fireRing, Direction.DOWN);
        scene.overlay().showText(60).text("Surround it with a ring of Soul Fire.")
                .pointAt(util.vector().blockSurface(new BlockPos(3, 1, 4), Direction.UP));
        scene.idle(70);

        // 3. Highlight the Fragrance Corners (from your NBT)
        BlockPos corner1 = new BlockPos(2, 1, 2); // Bastion
        BlockPos corner2 = new BlockPos(6, 1, 6); // Malice
        BlockPos corner3 = new BlockPos(2, 1, 6);
        BlockPos corner4 = new BlockPos(6, 1, 2);

        scene.addKeyframe();
        scene.world().showSection(util.select().position(corner1).add(util.select().position(corner2).add(util.select().position(corner3).add(util.select().position(corner4)))), Direction.DOWN);

        scene.overlay().showOutline(PonderPalette.BLUE, corner1, util.select().position(corner1), 60);
        scene.overlay().showOutline(PonderPalette.BLUE, corner2, util.select().position(corner2), 60);
        scene.overlay().showOutline(PonderPalette.GREEN, corner3, util.select().position(corner3), 60);
        scene.overlay().showOutline(PonderPalette.GREEN, corner4, util.select().position(corner4), 60);
        scene.overlay().showText(30).text("Place two different Fragrances in opposite corners.")
                .colored(PonderPalette.BLUE).pointAt(util.vector().blockSurface(corner1, Direction.UP));
        scene.overlay().showText(30).text("Place Empty Fragrance Casings in the other opposite corners.")
                        .colored(PonderPalette.GREEN).pointAt(util.vector().blockSurface(corner4, Direction.UP));
        scene.idle(70);

        // 4. Show the Activation and Danger Zone
        scene.addKeyframe();
        scene.overlay().showText(30).text("Right-click to start. The ritual lasts 5 minutes.")
                .colored(PonderPalette.RED).pointAt(util.vector().blockSurface(anchorPos, Direction.UP));

        scene.idle(40);
        scene.overlay().showText(60).text("Warning: The surrounding zone will repel entities and apply Wither.")
                .independent(40);

        scene.idle(60);

        scene.addKeyframe();
        scene.overlay().showOutline(PonderPalette.RED, anchorPos, util.select().position(anchorPos), 120);
        scene.overlay().showText(40).text("The anchor will turn into an Intensified Anchor" )
                .colored(PonderPalette.RED).pointAt(util.vector().blockSurface(anchorPos, Direction.DOWN));
        scene.idle(40);

        scene.addKeyframe();
        scene.overlay().showText(40).text("The Effects of the Intensified Anchor:\n- Resistance II\n- Regeneration II\n- Ghast Passivity\n- Wither Immunity" )
                .colored(PonderPalette.WHITE).pointAt(util.vector().blockSurface(anchorPos, Direction.UP));
        scene.idle(40);
    }

    public static void cinderScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("cinder", "The Ember of the Nether");
        scene.configureBasePlate(0, 0, 9);
        // Updated to match your NBT: [4, 1, 4] is the center fragrance
        BlockPos centerPos = new BlockPos(4, 1, 4);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        scene.addKeyframe();
        scene.world().showSection(util.select().position(centerPos), Direction.DOWN);
        scene.idle(15);
        scene.overlay().showText(40).text("createbeyondlimits.ponder.cinder.text_1").independent(40);
        renderPulse(scene, util, centerPos);

        scene.addKeyframe();
        // Reduced duration from 80 to 50 so it disappears before the conflict phase
        scene.overlay().showText(50).text("createbeyondlimits.ponder.cinder.text_2")
                .colored(PonderPalette.GREEN).pointAt(util.vector().blockSurface(centerPos, Direction.UP));
        renderParticles(scene, util, centerPos, ParticleTypes.SOUL_FIRE_FLAME);

        renderSymmetricalOverbind(scene, util, centerPos, "cinder");
    }

    public static void maliceScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("malice", "The Vigor of Hatred");
        scene.configureBasePlate(0, 0, 9);
        BlockPos centerPos = new BlockPos(4, 1, 4);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        scene.addKeyframe();
        scene.world().showSection(util.select().position(centerPos), Direction.DOWN);
        scene.idle(15);
        scene.overlay().showText(40).text("createbeyondlimits.ponder.malice.text_1").independent(40);
        renderPulse(scene, util, centerPos);

        scene.addKeyframe();
        scene.overlay().showText(50).text("createbeyondlimits.ponder.malice.text_2")
                .colored(PonderPalette.RED).pointAt(util.vector().blockSurface(centerPos, Direction.UP));
        renderParticles(scene, util, centerPos, ParticleTypes.ANGRY_VILLAGER);

        renderSymmetricalOverbind(scene, util, centerPos, "malice");
    }

    public static void bastionScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("bastion", "The Shield of the Bastion");
        scene.configureBasePlate(0, 0, 9);
        BlockPos centerPos = new BlockPos(4, 1, 4);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        scene.addKeyframe();
        scene.world().showSection(util.select().position(centerPos), Direction.DOWN);
        scene.idle(15);
        scene.overlay().showText(40).text("createbeyondlimits.ponder.bastion.text_1").independent(40);
        renderPulse(scene, util, centerPos);

        scene.addKeyframe();
        // Fixed duration to prevent overlapping with Phase 3
        scene.overlay().showText(50).text("createbeyondlimits.ponder.bastion.text_2")
                .colored(PonderPalette.GREEN).pointAt(util.vector().blockSurface(centerPos, Direction.UP));
        renderParticles(scene, util, centerPos, ParticleTypes.HAPPY_VILLAGER);

        renderSymmetricalOverbind(scene, util, centerPos, "bastion");
    }

    private static void renderPulse(SceneBuilder scene, SceneBuildingUtil util, BlockPos pos) {
        for(int i = 0; i < 12; i++) {
            double angle = i * (Math.PI * 2 / 12);
            scene.effects().emitParticles(util.vector().centerOf(pos),
                    scene.effects().simpleParticleEmitter(ParticleTypes.PORTAL, new Vec3(Math.cos(angle) * 0.2, 0, Math.sin(angle) * 0.2)), 1, 1);
            if(i % 3 == 0) scene.idle(2);
        }
        scene.idle(10);
    }

    private static void renderParticles(SceneBuilder scene, SceneBuildingUtil util, BlockPos pos, net.minecraft.core.particles.ParticleOptions type) {
        for(int i = 0; i < 4; i++) {
            scene.effects().emitParticles(util.vector().centerOf(pos).add(0, 0.5, 0),
                    scene.effects().simpleParticleEmitter(type, Vec3.ZERO), 4, 1);
            scene.idle(10);
        }
    }

    private static void renderSymmetricalOverbind(SceneBuilder scene, SceneBuildingUtil util, BlockPos centerPos, String id) {
        scene.addKeyframe();
        // Updated coordinates to match NBT: 1 and 7 on the X axis
        BlockPos leftPos = new BlockPos(1, 1, 4);
        BlockPos rightPos = new BlockPos(7, 1, 4);

        Selection centerSel = util.select().position(centerPos);
        Selection leftSel = util.select().position(leftPos);
        Selection rightSel = util.select().position(rightPos);

        // 1. Conflict Warning
        scene.world().showSection(leftSel.add(rightSel), Direction.DOWN);
        scene.idle(15);
        scene.overlay().showOutline(PonderPalette.RED, leftPos, leftSel, 30);
        scene.overlay().showOutline(PonderPalette.RED, rightPos, rightSel, 30);
        scene.overlay().showText(40).text("createbeyondlimits.ponder." + id + ".text_3")
                .colored(PonderPalette.RED).placeNearTarget();
        scene.idle(25);

        // 2. Damage Indicator
        scene.overlay().showText(40).text("createbeyondlimits.ponder." + id + ".text_4")
                .pointAt(util.vector().blockSurface(rightPos, Direction.UP));
        scene.effects().emitParticles(util.vector().centerOf(rightPos),
                scene.effects().simpleParticleEmitter(ParticleTypes.DAMAGE_INDICATOR, Vec3.ZERO), 4, 1);
        scene.idle(35);

        // 3. Highlight Original for destruction
        scene.addKeyframe();
        scene.overlay().showOutline(PonderPalette.WHITE, centerPos, centerSel, 30);
        scene.overlay().showText(40).text("createbeyondlimits.ponder." + id + ".text_5")
                .pointAt(util.vector().blockSurface(centerPos, Direction.UP));
        scene.idle(25);

        // 4. Destroy Center (Text and Block disappear together)
        scene.world().hideSection(centerSel, Direction.UP);
        scene.effects().emitParticles(util.vector().centerOf(centerPos),
                scene.effects().simpleParticleEmitter(ParticleTypes.LARGE_SMOKE, Vec3.ZERO), 10, 1);
        scene.idle(15);

        // 5. Claim New
        scene.addKeyframe();
        scene.overlay().showText(40).text("createbeyondlimits.ponder." + id + ".text_6")
                .colored(PonderPalette.GREEN).pointAt(util.vector().blockSurface(leftPos, Direction.UP));
        renderPulse(scene, util, leftPos);
        scene.idle(25);

        // 6. Lock demonstration
        scene.overlay().showOutline(PonderPalette.RED, rightPos, rightSel, 40);
        scene.overlay().showText(50).text("createbeyondlimits.ponder." + id + ".text_7")
                .colored(PonderPalette.RED).pointAt(util.vector().blockSurface(rightPos, Direction.WEST));
        scene.effects().emitParticles(util.vector().centerOf(rightPos),
                scene.effects().simpleParticleEmitter(ParticleTypes.DAMAGE_INDICATOR, Vec3.ZERO), 2, 1);
        scene.idle(20);
    }


    public static void aromaDisperserScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("aroma_disperser", "Setting up the Aroma Disperser");
        // Using an odd number ensures Ponder centers the scene correctly
        scene.configureBasePlate(0, 0, 9);

        // YOUR ORIGINAL, CORRECT COORDINATES
        BlockPos disperserPos = new BlockPos(4, 3, 4);
        BlockPos motorPos = new BlockPos(6, 0, 8);
        BlockPos stressPos = new BlockPos(2, 2, 3); // Swapped to match NBT State 5
        BlockPos speedPos = new BlockPos(6, 2, 3);  // Swapped to match NBT State 10

        // --- STEP 1: BASE PLATE ---
        scene.addKeyframe();
        // Grab the actual floor at Y=1, but subtract the cogwheel that sits at [5, 1, 9] so it doesn't spawn early
        Selection floor = util.select().layer(1).substract(util.select().position(5, 1, 9));
        scene.world().showSection(floor, Direction.UP);
        scene.idle(15);

        // --- STEP 2: PIECE-BY-PIECE MACHINERY ASSEMBLY ---
        scene.addKeyframe();

        // Show speedometer and stressometer gauges first
        scene.world().showSection(util.select().position(6, 2, 3), Direction.DOWN);
        scene.world().showSection(util.select().position(2, 2, 3), Direction.DOWN);
        scene.idle(8);

        // Show the shafts connecting from the gauges one by one
        scene.world().showSection(util.select().position(5, 2, 3), Direction.SOUTH);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 2, 3), Direction.SOUTH);
        scene.idle(8);

        // Show the input gearboxes drops
        scene.world().showSection(util.select().position(4, 2, 3), Direction.DOWN);
        scene.world().showSection(util.select().position(4, 2, 4), Direction.DOWN);
        scene.idle(8);

        // Roll out the transmission shaft line block by block towards the back
        for (int z = 5; z <= 9; z++) {
            scene.world().showSection(util.select().position(4, 2, z), Direction.NORTH);
            scene.idle(4);
        }

        // Drop the vertical power connector line blocks down to the motor base
        // Including the cogwheel at [5, 1, 9] and the Y=0 motor pieces
        scene.world().showSection(util.select().position(5, 1, 9), Direction.DOWN);
        scene.world().showSection(util.select().position(6, 0, 9), Direction.DOWN);
        scene.world().showSection(util.select().position(motorPos), Direction.DOWN);
        scene.idle(15);

        // --- STEP 3: PLACE THE MAIN DISPERSER UNIT ---
        scene.addKeyframe();
        scene.world().showSection(util.select().position(disperserPos), Direction.DOWN);
        scene.idle(15);

        // Highlight the disperser
        scene.overlay().showOutline(PonderPalette.BLUE, disperserPos, util.select().position(disperserPos), 50);
        scene.overlay().showText(60)
                .text("The Aroma Disperser uses rotational power to spread potion effects through the air.")
                .pointAt(util.vector().blockSurface(disperserPos, Direction.UP));
        scene.idle(65);

        // --- STEP 4: HOW THE INLET INTAKE WORKS ---
        scene.addKeyframe();
        scene.overlay().showOutline(PonderPalette.BLUE, disperserPos, util.select().position(disperserPos), 50);
        scene.overlay().showText(60)
                .text("Put items into the front slots to act as a filter for your potion clouds.")
                .colored(PonderPalette.BLUE)
                .pointAt(util.vector().blockSurface(disperserPos, Direction.WEST));
        scene.idle(65);

        // --- STEP 5: VISUALIZE FULL 3D RECTANGLE RANGE (8x8x8 CUBE) ---
        scene.addKeyframe();

        // Target an 8x8x8 box volume centered around the unit
        Selection full3DBoxArea = util.select().fromTo(
                disperserPos.getX() - 4, disperserPos.getY() - 4, disperserPos.getZ() - 4,
                disperserPos.getX() + 4, disperserPos.getY() + 4, disperserPos.getZ() + 4
        );

        scene.overlay().showOutline(PonderPalette.GREEN, disperserPos, full3DBoxArea, 80);
        scene.overlay().showText(70)
                .text("At 16 RPM the radius is 8 blocks. At 256 RPM the radius is 128 blocks.")
                .colored(PonderPalette.GREEN)
                .pointAt(util.vector().blockSurface(disperserPos, Direction.NORTH));
        scene.idle(75);

        scene.overlay().showOutline(PonderPalette.RED, stressPos, util.select().position(stressPos), 45);
        scene.overlay().showText(40)
                .text("At 16 RPM the Disperser uses 64 SU. At 256 it uses 1032!")
                .colored(PonderPalette.RED)
                .pointAt(util.vector().blockSurface(stressPos, Direction.SOUTH));
        scene.idle(20);

        scene.overlay().showOutline(PonderPalette.BLUE, speedPos, util.select().position(speedPos), 45);
        scene.overlay().showText(40)
                .text("Optimal RPM: 64")
                .colored(PonderPalette.BLUE)
                .pointAt(util.vector().blockSurface(speedPos, Direction.SOUTH));
        scene.idle(20);
    }
}