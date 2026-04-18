package com.example.event;

import com.example.component.JujutsuPlayerComponent;
import com.example.component.ModComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;

public class DamageEventHandler {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof Player player) {
                JujutsuPlayerComponent stats = ModComponents.JUJUTSU_PLAYER.get(player);
                if (stats.isInfinityActive()) {
                    return false; // Cancel damage
                }
            }
            return true;
        });
    }
}
