package com.nanaios.polygonal_tech.client.gui.parts;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface IGuiPart {
    default void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick,int leftPos, int topPos){

    }
    default void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, int leftPos, int topPos){
    }
    default void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY, int leftPos, int topPos, Font font) {
    }
}
