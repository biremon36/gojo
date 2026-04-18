package com.example.network;

import com.example.network.packet.SkillC2SPacket;
import com.example.network.packet.UpgradeC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class ModPackets {
    public static final ResourceLocation SKILL_PACKET_ID = new ResourceLocation("modid", "skill_packet");
    public static final ResourceLocation UPGRADE_PACKET_ID = new ResourceLocation("modid", "upgrade_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SKILL_PACKET_ID, SkillC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(UPGRADE_PACKET_ID, UpgradeC2SPacket::receive);
    }
}
