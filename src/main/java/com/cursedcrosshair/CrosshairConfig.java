package com.cursedcrosshair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;

@Mod.EventBusSubscriber(modid = CursedCrosshair.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CrosshairConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue STYLE;
    public static final ForgeConfigSpec.IntValue COLOR;
    public static final ForgeConfigSpec.IntValue SIZE;
    public static final ForgeConfigSpec.IntValue OFFSET_X;
    public static final ForgeConfigSpec.IntValue OFFSET_Y;
    public static final ForgeConfigSpec.BooleanValue GLOW;
    public static final ForgeConfigSpec.BooleanValue ENABLED;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        b.push("crosshair");
        ENABLED = b.define("enabled", true);
        STYLE = b.defineInRange("style", 0, 0, 3);
        COLOR = b.defineInRange("color", 0, 0, 5);
        SIZE = b.defineInRange("size", 100, 50, 200);
        OFFSET_X = b.defineInRange("offsetX", 0, -50, 50);
        OFFSET_Y = b.defineInRange("offsetY", 0, -50, 50);
        GLOW = b.define("glow", true);
        b.pop();
        SPEC = b.build();
    }

    private CrosshairConfig() {}
}
