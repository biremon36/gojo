package com.example.event;

import com.example.component.JujutsuPlayerComponent;
import com.example.component.ModComponents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class TickEventHandler {
    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            for (ServerPlayer player : world.players()) {
                JujutsuPlayerComponent stats = ModComponents.JUJUTSU_PLAYER.get(player);

                // Handle burnout
                if (stats.getBurnoutTicks() > 0) {
                    stats.setBurnoutTicks(stats.getBurnoutTicks() - 1);
                }

                // Mana regeneration
                if (player.tickCount % 20 == 0 && stats.getBurnoutTicks() == 0) {
                    if (stats.getMana() < stats.getMaxMana()) {
                        stats.setMana(stats.getMana() + stats.getManaRegen());
                    }
                }

                // Infinity Logic
                if (stats.isInfinityActive()) {
                    stats.setMana(stats.getMana() - 0.5f); // Drain mana

                    if (player.tickCount % 40 == 0) {
                        player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.INFINITY_HUM, net.minecraft.sounds.SoundSource.PLAYERS, 0.5f, 1.0f);
                    }

                    if (player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.0, player.getZ(), 2, 0.5, 1.0, 0.5, 0.0);
                    }

                    if (stats.getMana() <= 0) {
                        stats.setInfinityActive(false);
                        stats.setBurnoutTicks(6000); // 5 minutes burnout
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1)); // Slowness II
                        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2)); // Hunger III
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1)); // Weakness II
                    }
                }

                // RCT Logic
                if (stats.isRCTActive()) {
                    if (stats.getMana() >= 1.0f) { // 20 mana per second (1 mana per tick)
                        stats.setMana(stats.getMana() - 1.0f);
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 3)); // Regen IV

                        // Remove negative effects
                        player.removeEffect(MobEffects.POISON);
                        player.removeEffect(MobEffects.WITHER);

                        if (player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.0, player.getZ(), 1, 0.3, 0.5, 0.3, 0.0);
                        }
                    } else {
                        stats.setRCTActive(false);
                    }
                }

                // Purple charge logic
                if (stats.getPurpleChargeTicks() > 0) {
                    stats.setPurpleChargeTicks(stats.getPurpleChargeTicks() - 1);
                    if (stats.getPurpleChargeTicks() == 0) {
                        // Fire purple!
                        stats.setMana(0); // Takes 100% mana
                        player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.PURPLE_FIRE, net.minecraft.sounds.SoundSource.PLAYERS, 2.0f, 1.0f);
                        com.example.entity.PurpleEntity purple = new com.example.entity.PurpleEntity(com.example.entity.ModEntities.PURPLE_ENTITY, player.level());
                        purple.setOwner(player);
                        purple.setPos(player.getX() + player.getLookAngle().x * 2, player.getY() + 1.5, player.getZ() + player.getLookAngle().z * 2);
                        purple.setDeltaMovement(player.getLookAngle().scale(0.5));
                        player.level().addFreshEntity(purple);
                    }
                }
            }
        });
    }
}
