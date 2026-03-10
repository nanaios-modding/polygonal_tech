package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachineMk1;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.registries.impl.DeferredMenuTypeRegister;
import com.nanaios.polygonal_tech.registries.impl.MultipleMenuTypeRegister;
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
