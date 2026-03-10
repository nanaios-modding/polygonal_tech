package com.nanaios.polygonal_tech.client.gui.base;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BaseScreen<M extends BaseGuiMachine<M>> extends AbstractContainerScreen<BaseMenu<M>> {
    protected static final ResourceLocation BACK_GROUND = PolygonalTech.rl("textures/gui/background.png");

    public BaseScreen(BaseMenu<M> baseMenu, Inventory inventory, Component component) {
        super(baseMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();

        this.titleLabelX = (imageWidth - font.width(title)) / 2;
        this.titleLabelY = 5;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        graphics.blit(BACK_GROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
