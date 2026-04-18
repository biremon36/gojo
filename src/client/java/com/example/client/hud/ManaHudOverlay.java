package com.example.client.hud;

import com.example.component.JujutsuPlayerComponent;
import com.example.component.ModComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import com.mojang.blaze3d.systems.RenderSystem;

public class ManaHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        try {
            Minecraft client = Minecraft.getInstance();
            Player player = client.player;

            if (player != null && !player.isSpectator()) {
                JujutsuPlayerComponent stats = ModComponents.JUJUTSU_PLAYER.get(player);
                if (stats != null) {
                    float mana = stats.getMana();
                    float maxMana = stats.getMaxMana();

                    int width = client.getWindow().getGuiScaledWidth();
                    int height = client.getWindow().getGuiScaledHeight();

                    int x = width / 2 - 91;
                    int y = height - 45; // Just above the XP bar

                    // RenderSystem.enableBlend(); // Removing to avoid raw OpenGL calls that might cause crashes

                    // Draw background
                    drawContext.fill(x, y, x + 182, y + 5, 0xAA000000);

                    // Draw mana
                    int manaWidth = (int) ((mana / maxMana) * 182.0f);
                    int color = 0xFF0055FF; // Blue

                    // Change color if burnout
                    if (stats.getBurnoutTicks() > 0) {
                        color = 0xFF555555; // Gray
                    }

                    drawContext.fill(x, y, x + manaWidth, y + 5, color);

                    // Draw text
                    String text = (int)mana + "/" + (int)maxMana;
                    drawContext.drawString(client.font, text, x + 91 - client.font.width(text) / 2, y - 8, 0xFFFFFF, true);

                    // RenderSystem.disableBlend();
                }
            }
        } catch (Exception e) {
            // HUD render fail-safe
        }
    }
}
