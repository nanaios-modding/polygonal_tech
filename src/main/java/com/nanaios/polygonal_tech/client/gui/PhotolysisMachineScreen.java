package com.nanaios.polygonal_tech.client.gui;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachine;
import com.nanaios.polygonal_tech.client.gui.base.BaseScreen;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PhotolysisMachineScreen extends BaseScreen<PhotolysisMachine> {
    public PhotolysisMachineScreen(BaseMenu<PhotolysisMachine> baseMenu, Inventory inventory, Component component) {
        super(baseMenu, inventory, component);
    }
}
