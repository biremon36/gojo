package com.example.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent INFINITY_HUM = registerSoundEvent("infinity_hum");
    public static final SoundEvent BLUE_CAST = registerSoundEvent("blue_cast");
    public static final SoundEvent RED_CAST = registerSoundEvent("red_cast");
    public static final SoundEvent PURPLE_CHARGE = registerSoundEvent("purple_charge");
    public static final SoundEvent PURPLE_FIRE = registerSoundEvent("purple_fire");
    public static final SoundEvent DOMAIN_EXPANSION = registerSoundEvent("domain_expansion");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation("modid", name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        // Initializes the class
    }
}
