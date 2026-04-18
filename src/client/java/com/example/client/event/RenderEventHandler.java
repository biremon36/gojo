package com.example.client.event;

import com.example.component.JujutsuPlayerComponent;
import com.example.component.ModComponents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RenderEventHandler {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null) return;

            Entity targetedEntity = client.crosshairPickEntity;
            if (targetedEntity instanceof Player targetPlayer) {
                JujutsuPlayerComponent stats = ModComponents.JUJUTSU_PLAYER.get(targetPlayer);
                if (stats != null) {
                    // Draw nameplate for mana
                    float mana = stats.getMana();
                    float maxMana = stats.getMaxMana();
                    String manaText = String.format("Mana: %d/%d", (int)mana, (int)maxMana);

                    com.mojang.blaze3d.vertex.PoseStack poseStack = context.matrixStack();
                    poseStack.pushPose();

                    double x = targetPlayer.getX() - client.getEntityRenderDispatcher().camera.getPosition().x;
                    double y = targetPlayer.getY() + targetPlayer.getBbHeight() + 0.5 - client.getEntityRenderDispatcher().camera.getPosition().y;
                    double z = targetPlayer.getZ() - client.getEntityRenderDispatcher().camera.getPosition().z;

                    poseStack.translate(x, y, z);
                    poseStack.mulPose(client.getEntityRenderDispatcher().camera.rotation());
                    poseStack.scale(-0.025F, -0.025F, 0.025F);

                    org.joml.Matrix4f matrix4f = poseStack.last().pose();
                    float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                    int backgroundColor = (int)(backgroundOpacity * 255.0F) << 24;

                    net.minecraft.client.gui.Font font = client.font;
                    float textWidth = (float)(-font.width(manaText) / 2);

                    net.minecraft.client.renderer.MultiBufferSource.BufferSource bufferSource = client.renderBuffers().bufferSource();
                    font.drawInBatch(manaText, textWidth, 0, 0x00AFFF, false, matrix4f, bufferSource, net.minecraft.client.gui.Font.DisplayMode.NORMAL, backgroundColor, 15728880);

                    poseStack.popPose();
                }
            }
        });
    }
}
