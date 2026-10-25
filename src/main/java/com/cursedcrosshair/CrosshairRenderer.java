package com.cursedcrosshair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CursedCrosshair.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class CrosshairRenderer {
    private CrosshairRenderer() {}

    public static int colorFor(int color) {
        return switch (color) {
            case 1 -> 0xFF9B4DFF;
            case 2 -> 0xFF35C9FF;
            case 3 -> 0xFFFFFFFF;
            case 4 -> 0xFFFF2929;
            case 5 -> 0xFF50FF70;
            default -> 0xFFEF172B;
        };
    }

    @SubscribeEvent
    public static void renderCrosshair(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || !CrosshairConfig.ENABLED.get()) return;
        event.setCanceled(true);
        GuiGraphics g = event.getGuiGraphics();
        int cx = event.getWindow().getGuiScaledWidth() / 2 + CrosshairConfig.OFFSET_X.get();
        int cy = event.getWindow().getGuiScaledHeight() / 2 + CrosshairConfig.OFFSET_Y.get();
        int scale = CrosshairConfig.SIZE.get();
        int length = Math.max(3, 5 * scale / 100);
        int gap = Math.max(2, 2 * scale / 100);
        int color = colorFor(CrosshairConfig.COLOR.get());
        int black = 0xCC080308;
        int[][] lines = {
            {cx-length,cy-1,cx-gap,cy+1}, {cx+gap,cy-1,cx+length,cy+1},
            {cx-1,cy-length,cx+1,cy-gap}, {cx-1,cy+gap,cx+1,cy+length}
        };
        if (CrosshairConfig.GLOW.get()) {
            for (int[] l : lines) g.fill(l[0]-1,l[1]-1,l[2]+1,l[3]+1,0x553F0000);
        }
        for (int[] l : lines) {
            g.fill(l[0]-1,l[1]-1,l[2]+1,l[3]+1,black);
            g.fill(l[0],l[1],l[2],l[3],color);
        }
        // Center remains open for precision aiming.
        if (CrosshairConfig.STYLE.get() == 1) {
            g.fill(cx-1,cy-1,cx+1,cy+1,color);
        } else if (CrosshairConfig.STYLE.get() == 2) {
            g.fill(cx-3,cy-3,cx+3,cy-2,color);
            g.fill(cx-3,cy+2,cx+3,cy+3,color);
            g.fill(cx-3,cy-2,cx-2,cy+2,color);
            g.fill(cx+2,cy-2,cx+3,cy+2,color);
        } else if (CrosshairConfig.STYLE.get() == 3) {
            g.fill(cx-4,cy-4,cx-2,cy-2,color);
            g.fill(cx+2,cy-4,cx+4,cy-2,color);
            g.fill(cx-4,cy+2,cx-2,cy+4,color);
            g.fill(cx+2,cy+2,cx+4,cy+4,color);
        }
    }
}
