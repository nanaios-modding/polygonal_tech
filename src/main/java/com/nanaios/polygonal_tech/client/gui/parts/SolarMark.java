package com.nanaios.polygonal_tech.client.gui.parts;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BooleanSupplier;

public class SolarMark implements IGuiPart{
    private static final ResourceLocation SOLAR_ON = PolygonalTech.rl("textures/gui/solar_on.png");
    private static final ResourceLocation SOLAR_OFF = PolygonalTech.rl("textures/gui/solar_off.png");

    private final int x;
    private final int y;
    private final BooleanSupplier solarActiveSupplier;

    public SolarMark(int x, int y, BooleanSupplier solarActiveSupplier) {
        this.x = x;
        this.y = y;
        this.solarActiveSupplier = solarActiveSupplier;
    }

    @Override
    public void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, int leftPos, int topPos) {
        boolean solarActive = solarActiveSupplier.getAsBoolean();
        ResourceLocation texture = solarActive ? SOLAR_ON : SOLAR_OFF;
        graphics.blit(texture, leftPos + x, topPos + y, 0, 0, 16, 16, 16, 16);
    }
}
