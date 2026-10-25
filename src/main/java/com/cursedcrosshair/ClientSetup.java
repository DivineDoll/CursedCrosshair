package com.cursedcrosshair;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = CursedCrosshair.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientSetup {
    public static KeyMapping OPEN_CONFIG;

    private ClientSetup() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientEvents.class);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        OPEN_CONFIG = new KeyMapping(
            "key.cursedcrosshair.open_config",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.categories.cursedcrosshair"
        );
        event.register(OPEN_CONFIG);
    }

    @Mod.EventBusSubscriber(modid = CursedCrosshair.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onKeyInput(net.minecraftforge.client.event.InputEvent.Key event) {
            if (OPEN_CONFIG != null && OPEN_CONFIG.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.screen == null) mc.setScreen(new CrosshairConfigScreen(null));
            }
        }

        @SubscribeEvent
        public static void onOptionsScreen(ScreenEvent.Init.Post event) {
            if (!(event.getScreen() instanceof OptionsScreen)) return;
            var screen = event.getScreen();
            int x = screen.width / 2 + 5;
            int y = screen.height / 6 + 144 - 6;
            event.addListener(net.minecraft.client.gui.components.Button.builder(
                Component.literal("Cursed Crosshair"),
                button -> Minecraft.getInstance().setScreen(new CrosshairConfigScreen(screen))
            ).bounds(x, y, 150, 20).build());
        }
    }
}
