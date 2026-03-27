package com.nanaios.polygonal_tech.main.client;

import com.nanaios.polygonal_tech.main.client.gui.PhotolysisMachineMk1Screen;
import com.nanaios.polygonal_tech.main.registries.PolygonalTechMenuTypeRegister;
import net.minecraft.client.gui.screens.MenuScreens;

public class PolygonalTechScreen {
    public static void registerScreens() {
        MenuScreens.register(
                PolygonalTechMenuTypeRegister.PHOTOLYSIS_MACHINE_MENU.get(),
                PhotolysisMachineMk1Screen::new
        );
    }
}
