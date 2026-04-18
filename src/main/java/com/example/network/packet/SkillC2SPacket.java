package com.example.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class SkillC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        String skillType = buf.readUtf();
        boolean isKeyDown = buf.readBoolean();

        server.execute(() -> {
            com.example.component.JujutsuPlayerComponent stats = com.example.component.ModComponents.JUJUTSU_PLAYER.get(player);
            if (stats == null) return;

            if (isKeyDown) {
                switch (skillType) {
                    case "infinity":
                        if (stats.getBurnoutTicks() == 0 && stats.getMana() > 0) {
                            stats.setInfinityActive(!stats.isInfinityActive());
                        }
                        break;
                    case "rct":
                        if (stats.getBurnoutTicks() == 0 && stats.getMana() >= 20) {
                            stats.setRCTActive(!stats.isRCTActive());
                        }
                        break;
                    case "blue":
                        if (stats.hasBlue() && stats.getBurnoutTicks() == 0 && stats.getMana() >= 10) {
                            stats.setMana(stats.getMana() - 10);
                            player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.BLUE_CAST, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
                            com.example.entity.BlueEntity blue = new com.example.entity.BlueEntity(com.example.entity.ModEntities.BLUE_ENTITY, player.level());
                            blue.setOwner(player);
                            blue.setPos(player.getX() + player.getLookAngle().x * 2, player.getY() + 1.5, player.getZ() + player.getLookAngle().z * 2);
                            player.level().addFreshEntity(blue);
                        }
                        break;
                    case "red":
                        if (stats.hasRed() && stats.getBurnoutTicks() == 0 && stats.getMana() >= 20) {
                            stats.setMana(stats.getMana() - 20);
                            com.example.skill.RedSkill.cast(player);
                        }
                        break;
                    case "purple_charge":
                        if (stats.hasPurple() && stats.getBurnoutTicks() == 0 && stats.getMana() == stats.getMaxMana() && stats.getPurpleChargeTicks() == 0) {
                            stats.setPurpleChargeTicks(60); // 3 seconds
                            player.level().playSound(null, player.blockPosition(), com.example.sound.ModSounds.PURPLE_CHARGE, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
                        }
                        break;
                    case "domain":
                        if (stats.hasDomain() && stats.getBurnoutTicks() == 0 && stats.getMana() >= 50) {
                            stats.setMana(stats.getMana() - 50);
                            com.example.skill.DomainExpansion.cast(player);
                        }
                        break;
                }
            } else {
                if (skillType.equals("purple_charge")) {
                    stats.setPurpleChargeTicks(0); // Cancel charge if key released early
                }
            }
        });
    }
}
