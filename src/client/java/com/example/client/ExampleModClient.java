package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import com.example.client.gui.SkillTreeScreen;
import com.example.client.gui.SkillRadialScreen;
import com.example.client.hud.ManaHudOverlay;
import com.example.client.render.BlueEntityRenderer;
import com.example.client.render.PurpleEntityRenderer;
import com.example.entity.ModEntities;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModKeybinds.register();

		EntityRendererRegistry.register(ModEntities.BLUE_ENTITY, BlueEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.PURPLE_ENTITY, PurpleEntityRenderer::new);

		HudRenderCallback.EVENT.register(new ManaHudOverlay());

		com.example.client.event.RenderEventHandler.register();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (ModKeybinds.openSkillTreeKey.consumeClick()) {
				client.setScreen(new SkillTreeScreen());
			}

			if (ModKeybinds.radialMenuKey.consumeClick()) {
			    if (client.screen == null) {
			        client.setScreen(new SkillRadialScreen());
			    }
			}
		});
	}
}