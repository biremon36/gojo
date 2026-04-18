package com.example.client.render;

import com.example.entity.PurpleEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.particles.ParticleTypes;

public class PurpleEntityRenderer extends EntityRenderer<PurpleEntity> {
    public PurpleEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(PurpleEntity entity) {
        return new ResourceLocation("modid", "textures/entity/purple.png");
    }

    @Override
    public boolean shouldRender(PurpleEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public void render(PurpleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        if (entity.level().isClientSide) {
            // Draw particles at the edge of the 2-block radius hitbox
            for (int i = 0; i < 10; i++) {
                double angle = Math.random() * Math.PI * 2;
                double x = entity.getX() + Math.cos(angle) * 2.0;
                double z = entity.getZ() + Math.sin(angle) * 2.0;
                double y = entity.getY() + Math.random() * 2.0;
                entity.level().addParticle(ParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
            }
            entity.level().addParticle(ParticleTypes.DRAGON_BREATH, entity.getRandomX(3.0D), entity.getRandomY(), entity.getRandomZ(3.0D), 0.0D, 0.0D, 0.0D);
        }
    }
}
