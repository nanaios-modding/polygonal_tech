package com.nanaios.polygonal_tech.menu.base;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.registries.PolygonalTechMenuTypeRegister;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class BaseMenu<M extends BaseGuiMachine<?>> extends AbstractContainerMenu {
    public M blockEntity;

    public BaseMenu(M blockEntity, Inventory inv, int containerId) {
        super(PolygonalTechMenuTypeRegister.BASE_MACHINE_MENU.get(), containerId);
        this.blockEntity = blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
