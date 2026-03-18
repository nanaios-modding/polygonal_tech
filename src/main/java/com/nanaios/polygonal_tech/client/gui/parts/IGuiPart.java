package com.nanaios.polygonal_tech.client.gui.parts;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface IGuiPart {
    void render(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, int leftPos, int topPos);

    default void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY, int leftPos, int topPos, Font font) {
    }
}
