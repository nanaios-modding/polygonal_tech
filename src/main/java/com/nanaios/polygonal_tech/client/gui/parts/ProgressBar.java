package com.nanaios.polygonal_tech.client.gui.parts;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.function.LongSupplier;

public class ProgressBar implements IGuiPart{
    private static final int ARROW_WIDTH = 24;
    private static final int ARROW_HEIGHT = 16;
    private static final ResourceLocation PROGRESS_BAR_BG = PolygonalTech.rl("textures/gui/arrow_background.png");
    private static final ResourceLocation PROGRESS_BAR = PolygonalTech.rl("textures/gui/arrow_progress.png");

    private final int x;
    private final int y;
    private final LongSupplier progressSupplier;
    private final LongSupplier maxProgressSupplier;

    public ProgressBar(int x, int y,LongSupplier progressSupplier, LongSupplier maxProgressSupplier) {
        this.x = x;
        this.y = y;
        this.progressSupplier = progressSupplier;
        this.maxProgressSupplier = maxProgressSupplier;
    }

    @Override
    public void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, int leftPos, int topPos) {
        long progress = progressSupplier.getAsLong();
        long maxProgress = maxProgressSupplier.getAsLong();
        long progressWidth = maxProgress == 0 ? 0 : (long) ((double) progress / maxProgress * ARROW_WIDTH);

        graphics.blit(PROGRESS_BAR_BG, leftPos + x, topPos + y, 0, 0, ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
        graphics.blit(PROGRESS_BAR, leftPos + x, topPos + y, 0, 0, MathUtil.longToInt(progressWidth), ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT);
    }
}
