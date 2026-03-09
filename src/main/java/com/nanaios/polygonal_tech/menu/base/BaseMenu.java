package com.nanaios.polygonal_tech.menu.base;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.registries.PolygonalTechMenuTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class BaseMenu<M extends BaseGuiMachine<?>> extends AbstractContainerMenu {
    protected final BlockPos pos;
    protected final Inventory inventory;
    protected final ContainerLevelAccess access;

    public BaseMenu(int id, Inventory inv,BlockPos pos) {
        super(PolygonalTechMenuTypeRegister.BASE_MACHINE_MENU.get(), id);

        this.inventory = inv;
        this.pos = pos;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
    }

    @SuppressWarnings("unchecked")
    public M getMachine() {
        return (M) inventory.player.level().getBlockEntity(pos);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, inventory.player.level().getBlockState(pos).getBlock());
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }
}
