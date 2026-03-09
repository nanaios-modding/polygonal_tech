package com.nanaios.polygonal_tech.client.gui.base;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BaseScreen extends AbstractContainerScreen<BaseMenu<?>> {
    public BaseScreen(BaseMenu<?> baseMenu, Inventory inventory, Component component) {
        super(baseMenu, inventory, component);
        PolygonalTech.LOGGER.info("machine = {}",baseMenu.getMachine());
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {

    }
}
