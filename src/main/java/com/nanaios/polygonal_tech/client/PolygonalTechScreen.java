package com.nanaios.polygonal_tech.client;

import com.nanaios.polygonal_tech.client.gui.PhotolysisMachineScreen;
import com.nanaios.polygonal_tech.registries.PolygonalTechMenuTypeRegister;
import net.minecraft.client.gui.screens.MenuScreens;

public class PolygonalTechScreen {
    public static void registerScreens() {
        MenuScreens.register(
                PolygonalTechMenuTypeRegister.PHOTOLYSIS_MACHINE_MENU.get(),
                PhotolysisMachineScreen::new
        );
    }
}
