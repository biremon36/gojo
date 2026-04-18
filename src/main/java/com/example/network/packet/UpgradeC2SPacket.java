package com.example.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class UpgradeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        String upgradeType = buf.readUtf();

        server.execute(() -> {
            com.example.component.JujutsuPlayerComponent stats = com.example.component.ModComponents.JUJUTSU_PLAYER.get(player);
            if (stats == null) return;

            int level = player.experienceLevel;

            switch (upgradeType) {
                case "max_mana":
                    if (level >= 5) {
                        player.giveExperienceLevels(-5);
                        stats.setMaxMana(stats.getMaxMana() + 10);
                    }
                    break;
                case "unlock_blue":
                    if (!stats.hasBlue() && level >= 5) {
                        player.giveExperienceLevels(-5);
                        stats.setHasBlue(true);
                    }
                    break;
                case "unlock_red":
                    if (stats.hasBlue() && !stats.hasRed() && level >= 15) {
                        player.giveExperienceLevels(-15);
                        stats.setHasRed(true);
                    }
                    break;
                case "unlock_purple":
                    if (stats.hasRed() && !stats.hasPurple() && level >= 50) {
                        player.giveExperienceLevels(-50);
                        stats.setHasPurple(true);
                    }
                    break;
                case "unlock_domain":
                    if (!stats.hasDomain() && level >= 100) {
                        player.giveExperienceLevels(-100);
                        stats.setHasDomain(true);
                    }
                    break;
            }
        });
    }
}
