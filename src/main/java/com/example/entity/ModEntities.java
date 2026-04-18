package com.example.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.world.entity.EntityDimensions;

public class ModEntities {
    public static final EntityType<BlueEntity> BLUE_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation("modid", "blue_entity"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, BlueEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
                    .build()
    );

    public static final EntityType<PurpleEntity> PURPLE_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation("modid", "purple_entity"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, PurpleEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f))
                    .build()
    );

    public static void register() {
        // Just loading the class
    }
}
