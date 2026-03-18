package com.nanaios.polygonal_tech.client.gui.parts;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.PolygonalTechLang;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.client.gui.util.FluidRenderUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class FluidTankPart implements IGuiPart {
    private static final int TANK_BACKGROUND_COLOR = 0xFF8B8B8B;
    private static final ResourceLocation FLUID_TANK_COVER = PolygonalTech.rl("textures/gui/fluid_tank_cover.png");

    private static final int COVER_BORDER_SIZE = 1;
    private static final int COVER_WIDTH = 18;
    private static final int COVER_HEIGHT = 66;

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Supplier<ILongFluidTank> tankSupplier;

    public FluidTankPart(int x, int y, int width, int height, Supplier<ILongFluidTank> tankSupplier) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tankSupplier = tankSupplier;
    }

    @Override
    public void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, int leftPos, int topPos) {
        int tankX = leftPos + x;
        int tankY = topPos + y;

        graphics.fill(tankX, tankY, tankX + width, tankY + height, TANK_BACKGROUND_COLOR);

        ILongFluidTank tank = tankSupplier.get();
        if (tank != null) {
            FluidRenderUtil.renderFluidInTank(graphics, tank, tankX, tankY, width, height);
        }

        graphics.blit(
                FLUID_TANK_COVER,
                tankX - COVER_BORDER_SIZE,
                tankY - COVER_BORDER_SIZE,
                0,
                0,
                COVER_WIDTH,
                COVER_HEIGHT,
                COVER_WIDTH,
                COVER_HEIGHT
        );
    }

    @Override
    public void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY, int leftPos, int topPos, Font font) {
        int tankX = leftPos + x;
        int tankY = topPos + y;

        if (!isMouseOverTank(mouseX, mouseY, tankX, tankY)) {
            return;
        }

        ILongFluidTank tank = tankSupplier.get();
        if (tank == null) {
            return;
        }

        long amount = tank.getFluid().getLongAmount();
        long capacity = tank.getLongCapacity();

        List<Component> tooltipLines = new ArrayList<>();
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack.isEmpty()) {
            tooltipLines.add(PolygonalTechLang.FLUID_TANK_EMPTY.get());
        } else {
            tooltipLines.add(fluidStack.getDisplayName());
        }

        tooltipLines.add(PolygonalTechLang.FLUID_TANK_AMOUNT.get(formatLongValue(amount), formatLongValue(capacity)));

        List<FormattedCharSequence> formattedTooltipLines = new ArrayList<>();
        for (Component tooltipLine : tooltipLines) {
            formattedTooltipLines.add(tooltipLine.getVisualOrderText());
        }

        graphics.renderTooltip(font, formattedTooltipLines, mouseX, mouseY);
    }

    private boolean isMouseOverTank(int mouseX, int mouseY, int tankX, int tankY) {
        return mouseX >= tankX && mouseX < tankX + width && mouseY >= tankY && mouseY < tankY + height;
    }

    private String formatLongValue(long value) {
        return String.format(Locale.US, "%,d", value);
    }
}
