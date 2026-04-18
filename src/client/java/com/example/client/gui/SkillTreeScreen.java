package com.example.client.gui;

import com.example.network.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

public class SkillTreeScreen extends Screen {
    public SkillTreeScreen() {
        super(Component.literal("Jujutsu Skill Tree"));
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2;
        int y = this.height / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Upgrade Max Mana (5 XP)"), button -> {
            sendUpgradePacket("max_mana");
        }).bounds(x - 100, y - 60, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Unlock Blue (5 XP)"), button -> {
            sendUpgradePacket("unlock_blue");
        }).bounds(x - 100, y - 30, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Unlock Red (15 XP)"), button -> {
            sendUpgradePacket("unlock_red");
        }).bounds(x - 100, y, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Unlock Purple (50 XP)"), button -> {
            sendUpgradePacket("unlock_purple");
        }).bounds(x - 100, y + 30, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Unlock Domain (100 XP)"), button -> {
            sendUpgradePacket("unlock_domain");
        }).bounds(x - 100, y + 60, 200, 20).build());
    }

    private void sendUpgradePacket(String type) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(type);
        ClientPlayNetworking.send(ModPackets.UPGRADE_PACKET_ID, buf);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
