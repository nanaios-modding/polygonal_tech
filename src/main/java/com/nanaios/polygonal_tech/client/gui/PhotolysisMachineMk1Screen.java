package com.nanaios.polygonal_tech.client.gui;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachineMk1;
import com.nanaios.polygonal_tech.client.gui.base.BaseScreen;
import com.nanaios.polygonal_tech.client.gui.parts.FluidTank;
import com.nanaios.polygonal_tech.client.gui.parts.ProgressBar;
import com.nanaios.polygonal_tech.client.gui.parts.SolarMark;
import com.nanaios.polygonal_tech.config.PolygonalTechMachineConfig;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PhotolysisMachineMk1Screen extends BaseScreen<PhotolysisMachineMk1> {
    public PhotolysisMachineMk1Screen(BaseMenu<PhotolysisMachineMk1> baseMenu, Inventory inventory, Component component) {
        super(baseMenu, inventory, component);

        PhotolysisMachineMk1 machine = menu.getMachine();
        if (machine == null) return;

        guiParts.add(
                new SolarMark(
                        80, 20,
                        () -> machine.canSeeSky
                )
        );

        guiParts.add(new ProgressBar(
                78,40,
                machine::getProgress,
                () ->PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME.get()
        ));

        guiParts.add(new FluidTank(
                125, 15,
                16, 64,
                () -> machine.outputTank
        ));
    }
}
