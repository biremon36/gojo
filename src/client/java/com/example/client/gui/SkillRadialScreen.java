package com.example.client.gui;

import com.example.network.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public class SkillRadialScreen extends Screen {
    private final String[] skills = {"infinity", "rct", "blue", "red", "purple_charge", "domain"};
    private final String[] displayNames = {"Infinity", "RCT", "Blue", "Red", "Purple", "Domain Expansion"};
    private int selectedIndex = -1;

    public SkillRadialScreen() {
        super(Component.literal("Skill Menu"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.width / 2;
        int y = this.height / 2;

        RenderSystem.enableBlend();

        // Calculate selection
        double angle = Math.atan2(mouseY - y, mouseX - x);
        if (angle < 0) angle += Math.PI * 2;

        double segmentAngle = (Math.PI * 2) / skills.length;

        double dist = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));

        if (dist > 20) { // Deadzone
            selectedIndex = (int) ((angle + segmentAngle / 2) / segmentAngle) % skills.length;
        } else {
            selectedIndex = -1;
        }

        // Draw segments
        for (int i = 0; i < skills.length; i++) {
            double currentAngle = i * segmentAngle;
            double nextAngle = (i + 1) * segmentAngle;

            // Very simple rendering for demonstration
            int textX = x + (int)(Math.cos(currentAngle) * 60);
            int textY = y + (int)(Math.sin(currentAngle) * 60);

            int color = (i == selectedIndex) ? 0xFF00FF00 : 0xFFFFFFFF;
            guiGraphics.drawCenteredString(this.font, displayNames[i], textX, textY, color);
        }

        RenderSystem.disableBlend();
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (selectedIndex != -1 && button == 0) {
            sendSkillPacket(skills[selectedIndex], true);
            // Give time for press to register before release to avoid instant cancellation
            if (skills[selectedIndex].equals("purple_charge")) {
                // Wait is handled server side now by not instantly cancelling unless false is sent
            }
            Minecraft.getInstance().setScreen(null); // Close screen
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void sendSkillPacket(String type, boolean isDown) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(type);
        buf.writeBoolean(isDown);
        ClientPlayNetworking.send(ModPackets.SKILL_PACKET_ID, buf);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
