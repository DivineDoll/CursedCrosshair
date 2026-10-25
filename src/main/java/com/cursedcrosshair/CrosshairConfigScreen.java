package com.cursedcrosshair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CrosshairConfigScreen extends Screen {
    private final Screen parent;
    private int row = 0;

    public CrosshairConfigScreen(Screen parent) {
        super(Component.literal("Cursed Crosshair"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 100;
        int y = this.height / 4;
        addRenderableWidget(CycleButton.onOffBuilder(CrosshairConfig.ENABLED.get())
            .withMessage(Component.literal("Cruceta"))
            .create(left, y, 200, 20, Component.literal("Cruceta"),
                (button, value) -> CrosshairConfig.ENABLED.set(value)));
        y += 26;
        addRenderableWidget(CycleButton.<Integer>builder(v -> Component.literal("Diseño: " + styleName(v)))
            .withValues(0, 1, 2, 3)
            .withInitialValue(CrosshairConfig.STYLE.get())
            .create(left, y, 200, 20, Component.literal("Diseño"),
                (button, value) -> CrosshairConfig.STYLE.set(value)));
        y += 26;
        addRenderableWidget(CycleButton.<Integer>builder(v -> Component.literal("Color: " + colorName(v)))
            .withValues(0, 1, 2, 3, 4, 5)
            .withInitialValue(CrosshairConfig.COLOR.get())
            .create(left, y, 200, 20, Component.literal("Color"),
                (button, value) -> CrosshairConfig.COLOR.set(value)));
        y += 26;
        addRenderableWidget(new ValueSlider(left, y, 200, 20, "Tamaño", CrosshairConfig.SIZE.get(), 50, 200,
            v -> CrosshairConfig.SIZE.set(v)));
        y += 26;
        addRenderableWidget(new ValueSlider(left, y, 200, 20, "Posición X", CrosshairConfig.OFFSET_X.get(), -50, 50,
            v -> CrosshairConfig.OFFSET_X.set(v)));
        y += 26;
        addRenderableWidget(new ValueSlider(left, y, 200, 20, "Posición Y", CrosshairConfig.OFFSET_Y.get(), -50, 50,
            v -> CrosshairConfig.OFFSET_Y.set(v)));
        y += 26;
        addRenderableWidget(CycleButton.onOffBuilder(CrosshairConfig.GLOW.get())
            .withMessage(Component.literal("Resplandor"))
            .create(left, y, 200, 20, Component.literal("Resplandor"),
                (button, value) -> CrosshairConfig.GLOW.set(value)));
        addRenderableWidget(Button.builder(Component.literal("Volver"), b -> onClose())
            .bounds(left, this.height - 30, 200, 20).build());
    }

    private static String styleName(int i) {
        return switch (i) { case 1 -> "Cruz fina"; case 2 -> "Dominio"; case 3 -> "Partículas"; default -> "Sukuna"; };
    }
    private static String colorName(int i) {
        return switch (i) { case 1 -> "Morado"; case 2 -> "Azul"; case 3 -> "Blanco"; case 4 -> "Rojo brillante"; case 5 -> "Verde"; default -> "Rojo maldito"; };
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g);
        g.drawCenteredString(this.font, this.title, this.width / 2, 18, 0xFFFFFF);
        g.drawCenteredString(this.font, "Vista previa", this.width / 2, this.height / 4 - 18, 0xAAAAAA);
        int cx = this.width / 2 + CrosshairConfig.OFFSET_X.get();
        int cy = this.height / 4 - 5 + CrosshairConfig.OFFSET_Y.get();
        int color = CrosshairRenderer.colorFor(CrosshairConfig.COLOR.get());
        int size = Math.max(4, 5 * CrosshairConfig.SIZE.get() / 100);
        g.fill(cx - size, cy - 1, cx - 2, cy + 1, color);
        g.fill(cx + 2, cy - 1, cx + size, cy + 1, color);
        g.fill(cx - 1, cy - size, cx + 1, cy - 2, color);
        g.fill(cx - 1, cy + 2, cx + 1, cy + size, color);
        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        CrosshairConfig.SPEC.save();
        Minecraft.getInstance().setScreen(parent);
    }

    private static class ValueSlider extends AbstractSliderButton {
        private final String label;
        private final int min, max;
        private final java.util.function.IntConsumer consumer;
        private int value;
        ValueSlider(int x, int y, int w, int h, String label, int value, int min, int max,
                    java.util.function.IntConsumer consumer) {
            super(x, y, w, h, Component.empty(), (double)(value-min)/(max-min));
            this.label = label; this.value = value; this.min = min; this.max = max; this.consumer = consumer;
            updateMessage();
        }
        @Override protected void updateMessage() { setMessage(Component.literal(label + ": " + value + (label.equals("Tamaño") ? "%" : ""))); }
        @Override protected void applyValue() {
            value = min + (int)Math.round(this.value * (max-min));
            consumer.accept(value);
            updateMessage();
        }
    }
}
