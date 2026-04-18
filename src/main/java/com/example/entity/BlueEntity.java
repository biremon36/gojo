package com.example.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.phys.AABB;
import java.util.List;

import java.util.UUID;

public class BlueEntity extends Entity {
    private int lifespan = 100;
    private UUID ownerId = null;

    public BlueEntity(EntityType<?> type, Level level) {
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

        if (this.level().isClientSide) {
            // Client-side particle spawning at 20 ticks per second instead of render tick
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(1.5D), this.getRandomY(), this.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.ENCHANT, this.getRandomX(1.5D), this.getRandomY(), this.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
        } else {
            // Pull entities
            AABB area = this.getBoundingBox().inflate(5.0);
            List<Entity> entities = this.level().getEntities(this, area);

            for (Entity entity : entities) {
                if (this.ownerId != null && entity.getUUID().equals(this.ownerId)) {
                    continue; // Skip owner
                }

                double dx = this.getX() - entity.getX();
                double dy = this.getY() - entity.getY();
                double dz = this.getZ() - entity.getZ();
                double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);

                if (distance > 0.5) {
                    double pullStrength = 0.1;
                    entity.setDeltaMovement(entity.getDeltaMovement().add(
                        (dx / distance) * pullStrength,
                        (dy / distance) * pullStrength,
                        (dz / distance) * pullStrength
                    ));
                    entity.hasImpulse = true;
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
