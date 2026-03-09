package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.registries.impl.DeferredMenuTypeRegister;
import com.nanaios.polygonal_tech.registries.impl.MultipleMenuTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechMenuTypeRegister {
    public static MultipleMenuTypeRegister MENU_TYPES = new MultipleMenuTypeRegister();
    public static DeferredMenuTypeRegister MACHINE_GUI = MENU_TYPES.create();

    public static RegistryObject<MenuType<BaseMenu<?>>> BASE_MACHINE_MENU;

    static {
        BASE_MACHINE_MENU = MACHINE_GUI.register(
                "base_machine_menu",
                () -> IForgeMenuType.create((id, inv, buf) -> {

                    BlockPos pos = buf.readBlockPos();

                    return new BaseMenu<>(id, inv, pos);
                })
        );
    }
}
