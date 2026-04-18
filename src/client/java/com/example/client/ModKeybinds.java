package com.example.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static KeyMapping openSkillTreeKey;
    public static KeyMapping radialMenuKey;

    public static void register() {
        openSkillTreeKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.modid.open_skill_tree",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.modid.jujutsu"
        ));

        radialMenuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.modid.radial_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.modid.jujutsu"
        ));
    }
}
