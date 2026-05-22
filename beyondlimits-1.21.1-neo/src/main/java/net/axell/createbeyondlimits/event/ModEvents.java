package net.axell.createbeyondlimits.event;

import net.axell.createbeyondlimits.BeyondLimits;
import net.axell.createbeyondlimits.block.ModBlocks;
import net.axell.createbeyondlimits.config.ModConfigs;
import net.axell.createbeyondlimits.world.SoulLinkData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = BeyondLimits.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onGhastTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Ghast)) return;

        LivingEntity target = event.getOriginalAboutToBeSetTarget();

        if (target instanceof Player player) {
            if (player.level().dimension() == Level.NETHER && !player.level().isClientSide) {
                if (isAnchored(player)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWitherApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player
                && event.getEffectInstance().getEffect() == MobEffects.WITHER) {

            if (isAnchored(player)) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    @SubscribeEvent
    public static void onPiglinTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Piglin)) return;

        LivingEntity target = event.getOriginalAboutToBeSetTarget();

        if (target instanceof Player player) {
            if (player.level().dimension() == Level.NETHER && !player.level().isClientSide) {

                SoulLinkData data = SoulLinkData.get((ServerLevel) player.level());

                if (data.isPlayerBoundToType(player.getUUID(), "bastion")) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBruteAttack(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player
                && event.getSource().getEntity() instanceof PiglinBrute) {

            if (player.level().dimension() == Level.NETHER && !player.level().isClientSide) {

                SoulLinkData data = SoulLinkData.get((ServerLevel) player.level());

                if (data.isPlayerBoundToType(player.getUUID(), "bastion")) {
                    event.setAmount(event.getAmount() * 0.5f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAnimalTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Animal animal)) return;
        if (entity.level().isClientSide) return;

        if (!ModConfigs.ENABLE_NATURAL_BREEDING.get()) return;

        if (entity.tickCount % 400 != 0) return;

        if (animal.getAge() != 0 || animal.isInLove()) return;

        double roll = entity.level().random.nextDouble() * 100.0;

        if (roll >= ModConfigs.BREEDING_CHANCE_PERCENT.get()) return;

        List<Animal> partners =
                entity.level().getEntitiesOfClass(Animal.class, animal.getBoundingBox().inflate(8.0D));

        if (partners.size() < 2 || partners.size() >= 16) return;

        for (Animal partner : partners) {
            if (partner == animal) continue;

            if (partner.getClass() == animal.getClass()
                    && partner.getAge() == 0
                    && !partner.isInLove()) {

                animal.setInLove(null);
                partner.setInLove(null);
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (level.dimension() != Level.NETHER) return;

        if (!level.isClientSide) {
            SoulLinkData data = SoulLinkData.get((ServerLevel) level);
            handleLinkEffects((ServerPlayer) player, data, level);
        }

        if (isAnchored(player) && player.isInLava()) {
            Vec3 look = player.getLookAngle();
            double speedMult = 0.25;

            if (player.zza != 0 || player.xxa != 0) {
                Vec3 forward = look.scale(player.zza * speedMult);
                Vec3 side = look.yRot((float) Math.PI / 2f).scale(player.xxa * speedMult);

                Vec3 finalVel = forward.add(side);

                player.setDeltaMovement(finalVel.x, finalVel.y * 0.9, finalVel.z);
                player.hurtMarked = true;
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFog(ViewportEvent.RenderFog event) {
        if (event.getCamera().getEntity() instanceof Player player && player.isInLava()) {
            if (isAnchored(player)) {
                event.setNearPlaneDistance(4.0f);
                event.setFarPlaneDistance(24.0f);
                event.setCanceled(true);
            }
        }
    }

    private static void handleLinkEffects(ServerPlayer serverPlayer, SoulLinkData data, Level level) {
        Map<String, BlockPos> links = data.getAllLinks(serverPlayer.getUUID());

        for (String type : links.keySet()) {
            BlockPos pos = links.get(type);

            if (!level.getBlockState(pos).is(getBlockForType(type))) {
                data.removeLink(serverPlayer.getUUID(), type);
                serverPlayer.displayClientMessage(
                        Component.literal("§8The " + type + " link has been severed..."),
                        true
                );
                continue;
            }

            switch (type) {
                case "malice" -> {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 0, true, true));
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 120, 1, true, true));
                }
                case "cinder" -> {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 120, 0, true, true));
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 1, true, true));
                }
                case "bastion" -> {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 1, true, true));
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.LUCK, 120, 0, true, true));
                }
                case "anchor" -> {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 1, true, true));
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 1, true, true));
                }
            }
        }
    }

    private static boolean isAnchored(Player player) {
        return player.hasEffect(MobEffects.DAMAGE_RESISTANCE)
                && player.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() >= 1;
    }

    private static net.minecraft.world.level.block.Block getBlockForType(String type) {
        return switch (type) {
            case "malice" -> ModBlocks.FRAGRANCE_MALICE.get();
            case "cinder" -> ModBlocks.FRAGRANCE_CINDER.get();
            case "bastion" -> ModBlocks.FRAGRANCE_BASTION.get();
            case "anchor" -> ModBlocks.INTENSIFIED_ANCHOR.get();
            default -> net.minecraft.world.level.block.Blocks.AIR;
        };
    }
}