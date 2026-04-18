package com.example.client.render;

import com.example.entity.BlueEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.particles.ParticleTypes;

public class BlueEntityRenderer extends EntityRenderer<BlueEntity> {
    public BlueEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(BlueEntity entity) {
        return new ResourceLocation("modid", "textures/entity/blue.png");
    }

    @Override
    public boolean shouldRender(BlueEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public void render(BlueEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        if (entity.level().isClientSide) {
            entity.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getRandomX(1.5D), entity.getRandomY(), entity.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
            entity.level().addParticle(ParticleTypes.ENCHANT, entity.getRandomX(1.5D), entity.getRandomY(), entity.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
        }
    }
}
