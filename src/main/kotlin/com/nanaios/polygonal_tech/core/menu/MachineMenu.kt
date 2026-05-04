package com.nanaios.polygonal_tech.core.menu

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class MachineMenu(
    menuType: MenuType<*>,
    id: Int,
    level: Level,
    pos: BlockPos
): AbstractContainerMenu(menuType,id) {
    override fun quickMoveStack(player: Player, slot: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        return true
    }
}