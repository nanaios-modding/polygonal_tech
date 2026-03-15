package com.nanaios.polygonal_tech.client.gui;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachineMk1;
import com.nanaios.polygonal_tech.client.gui.base.BaseScreen;
import com.nanaios.polygonal_tech.client.gui.parts.ProgressBar;
import com.nanaios.polygonal_tech.config.PolygonalTechMachineConfig;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PhotolysisMachineMk1Screen extends BaseScreen<PhotolysisMachineMk1> {
    public PhotolysisMachineMk1Screen(BaseMenu<PhotolysisMachineMk1> baseMenu, Inventory inventory, Component component) {
        super(baseMenu, inventory, component);

        guiParts.add(new ProgressBar(
                60,33,
                () -> {
                    PhotolysisMachineMk1 machine = menu.getMachine();
                    if(machine == null) return 0;
                    return machine.getProgress();
                },
                () ->PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME.get()
        ));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        PhotolysisMachineMk1 machine = menu.getMachine();
        if(machine == null) return;
    }
}
