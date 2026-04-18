package com.example.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class JujutsuPlayerComponent implements Component, AutoSyncedComponent {
    private final Player player;

    private float mana = 100.0f;
    private float maxMana = 100.0f;
    private float manaRegen = 1.0f;

    private boolean hasBlue = false;
    private boolean hasRed = false;
    private boolean hasPurple = false;
    private boolean hasDomain = false;

    private boolean isInfinityActive = false;
    private boolean isRCTActive = false;

    private int burnoutTicks = 0;
    private int purpleChargeTicks = 0;

    public JujutsuPlayerComponent(Player player) {
        this.player = player;
    }

    public float getMana() { return mana; }
    public void setMana(float mana) {
        this.mana = Math.min(Math.max(mana, 0), maxMana);
        ModComponents.JUJUTSU_PLAYER.sync(player);
    }
    public float getMaxMana() { return maxMana; }
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
        ModComponents.JUJUTSU_PLAYER.sync(player);
    }
    public float getManaRegen() { return manaRegen; }

    public boolean hasBlue() { return hasBlue; }
    public void setHasBlue(boolean hasBlue) { this.hasBlue = hasBlue; ModComponents.JUJUTSU_PLAYER.sync(player); }
    public boolean hasRed() { return hasRed; }
    public void setHasRed(boolean hasRed) { this.hasRed = hasRed; ModComponents.JUJUTSU_PLAYER.sync(player); }
    public boolean hasPurple() { return hasPurple; }
    public void setHasPurple(boolean hasPurple) { this.hasPurple = hasPurple; ModComponents.JUJUTSU_PLAYER.sync(player); }
    public boolean hasDomain() { return hasDomain; }
    public void setHasDomain(boolean hasDomain) { this.hasDomain = hasDomain; ModComponents.JUJUTSU_PLAYER.sync(player); }

    public boolean isInfinityActive() { return isInfinityActive; }
    public void setInfinityActive(boolean active) { this.isInfinityActive = active; ModComponents.JUJUTSU_PLAYER.sync(player); }
    public boolean isRCTActive() { return isRCTActive; }
    public void setRCTActive(boolean active) { this.isRCTActive = active; ModComponents.JUJUTSU_PLAYER.sync(player); }

    public int getBurnoutTicks() { return burnoutTicks; }
    public void setBurnoutTicks(int ticks) { this.burnoutTicks = ticks; ModComponents.JUJUTSU_PLAYER.sync(player); }
    public int getPurpleChargeTicks() { return purpleChargeTicks; }
    public void setPurpleChargeTicks(int ticks) { this.purpleChargeTicks = ticks; ModComponents.JUJUTSU_PLAYER.sync(player); }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.mana = tag.getFloat("Mana");
        this.maxMana = tag.getFloat("MaxMana");
        if(this.maxMana == 0) this.maxMana = 100.0f; // Default if old save
        this.manaRegen = tag.getFloat("ManaRegen");
        if(this.manaRegen == 0) this.manaRegen = 1.0f;

        this.hasBlue = tag.getBoolean("HasBlue");
        this.hasRed = tag.getBoolean("HasRed");
        this.hasPurple = tag.getBoolean("HasPurple");
        this.hasDomain = tag.getBoolean("HasDomain");

        this.isInfinityActive = tag.getBoolean("InfinityActive");
        this.isRCTActive = tag.getBoolean("RCTActive");
        this.burnoutTicks = tag.getInt("BurnoutTicks");
        this.purpleChargeTicks = tag.getInt("PurpleChargeTicks");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putFloat("Mana", this.mana);
        tag.putFloat("MaxMana", this.maxMana);
        tag.putFloat("ManaRegen", this.manaRegen);

        tag.putBoolean("HasBlue", this.hasBlue);
        tag.putBoolean("HasRed", this.hasRed);
        tag.putBoolean("HasPurple", this.hasPurple);
        tag.putBoolean("HasDomain", this.hasDomain);

        tag.putBoolean("InfinityActive", this.isInfinityActive);
        tag.putBoolean("RCTActive", this.isRCTActive);
        tag.putInt("BurnoutTicks", this.burnoutTicks);
        tag.putInt("PurpleChargeTicks", this.purpleChargeTicks);
    }
}
