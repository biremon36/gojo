package com.example.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RedSkill {
    public static void cast(ServerPlayer player) {
        Vec3 look = player.getLookAngle();
        Vec3 pos = player.position().add(0, player.getEyeHeight(), 0);

        player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.RED_CAST, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);

        if (player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            for (int i = 0; i < 50; i++) {
                Vec3 particleDir = look.add((Math.random() - 0.5) * 1.5, (Math.random() - 0.5) * 1.5, (Math.random() - 0.5) * 1.5).normalize();
                serverLevel.sendParticles(new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(1.0f, 0.0f, 0.0f), 1.5f),
                    pos.x, pos.y, pos.z, 0, particleDir.x, particleDir.y, particleDir.z, 1.0);
            }
        }

        AABB area = player.getBoundingBox().inflate(10.0);
        List<Entity> entities = player.level().getEntities(player, area);

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                Vec3 targetDir = target.position().subtract(pos).normalize();
                double dot = look.dot(targetDir);

                // Cone of ~60 degrees (cos(30) = 0.866)
                if (dot > 0.866) {
                    double distance = pos.distanceTo(target.position());
                    double damage = Math.max(5.0, 20.0 - distance);
                    target.hurt(player.damageSources().playerAttack(player), (float) damage);

                    // Knockback
                    Vec3 knockback = targetDir.scale(2.0);
                    target.setDeltaMovement(target.getDeltaMovement().add(knockback));
                    target.hasImpulse = true;
                }
            }
        }
    }
}
