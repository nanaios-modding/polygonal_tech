package com.nanaios.polygonal_tech.main.registries;

import com.nanaios.polygonal_tech.main.block_entity.PhotolysisMachineMk1;
import com.nanaios.polygonal_tech.main.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredMenuTypeRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.MultipleMenuTypeRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechMenuTypeRegister {
    public static MultipleMenuTypeRegister MENU_TYPES = new MultipleMenuTypeRegister();
    public static DeferredMenuTypeRegister MACHINE_GUI = MENU_TYPES.create();

    public static RegistryObject<MenuType<BaseMenu<PhotolysisMachineMk1>>> PHOTOLYSIS_MACHINE_MENU;

    static {
        PHOTOLYSIS_MACHINE_MENU = MACHINE_GUI.register(PolygonalTechNamedTokens.PHOTOLYSIS_MACHINE_MK1);
    }
}
