package com.example.skill;

import com.example.component.JujutsuPlayerComponent;
import com.example.component.ModComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DomainExpansion {
    public static void cast(ServerPlayer player) {
        JujutsuPlayerComponent stats = ModComponents.JUJUTSU_PLAYER.get(player);

        player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.DOMAIN_EXPANSION, net.minecraft.sounds.SoundSource.PLAYERS, 2.0f, 1.0f);

        AABB area = player.getBoundingBox().inflate(15.0);
        List<Entity> entities = player.level().getEntities(player, area);

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target) {
                // Apply stun effect (Slowness 255 effectively stuns them, weakness prevents attacks)
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 255, false, false));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 255, false, false));
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 1, false, false));
            }
        }

        stats.setBurnoutTicks(6000); // 5 minutes (20 ticks * 60 seconds * 5)
    }
}
