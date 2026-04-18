package com.example.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.LivingEntity;
import java.util.List;

import java.util.UUID;

public class PurpleEntity extends Entity {
    private int lifespan = 200;
    private UUID ownerId = null;

    public PurpleEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public void setOwner(Entity owner) {
        if (owner != null) {
            this.ownerId = owner.getUUID();
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.lifespan = compound.getInt("Lifespan");
        if (compound.hasUUID("Owner")) {
            this.ownerId = compound.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Lifespan", this.lifespan);
        if (this.ownerId != null) {
            compound.putUUID("Owner", this.ownerId);
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Move slowly
        this.setPos(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);

        if (this.level().isClientSide) {
            // Client-side particle spawning at 20 ticks per second instead of render tick
            for (int i = 0; i < 5; i++) {
                double angle = Math.random() * Math.PI * 2;
                double px = this.getX() + Math.cos(angle) * 2.0;
                double pz = this.getZ() + Math.sin(angle) * 2.0;
                double py = this.getY() + Math.random() * 2.0;
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.PORTAL, px, py, pz, 0.0D, 0.0D, 0.0D);
            }
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH, this.getRandomX(3.0D), this.getRandomY(), this.getRandomZ(3.0D), 0.0D, 0.0D, 0.0D);
        } else {
            AABB area = this.getBoundingBox().inflate(2.0);
            List<Entity> entities = this.level().getEntities(this, area);

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity && entity != this) {
                    if (this.ownerId == null || !entity.getUUID().equals(this.ownerId)) {
                        entity.hurt(this.damageSources().magic(), 1000.0f); // Massive damage, basically kill
                    }
                }
            }

            this.lifespan--;
            if (this.lifespan <= 0) {
                this.discard();
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
