package com.example.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<JujutsuPlayerComponent> JUJUTSU_PLAYER =
            ComponentRegistry.getOrCreate(new ResourceLocation("modid", "jujutsu_player"), JujutsuPlayerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(JUJUTSU_PLAYER, JujutsuPlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
