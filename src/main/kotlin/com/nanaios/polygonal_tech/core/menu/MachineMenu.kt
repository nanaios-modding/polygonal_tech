package com.nanaios.polygonal_tech.core.menu

import com.nanaios.polygonal_tech.core.register.single.DeferredSingleBlockRegister
import com.nanaios.polygonal_tech.core.tile.SingleGuiMachineTile
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack

open class MachineMenu(
    protected val location: ResourceLocation,
    menuType: MenuType<*>,
    windowId: Int,
    protected val inventory: Inventory,
    protected val pos: BlockPos,
): AbstractContainerMenu(menuType,windowId) {
    constructor(
        location: ResourceLocation,
        menuType: MenuType<*>,
        windowId: Int,
        inventory: Inventory,
        buf: FriendlyByteBuf
    ):this(location,menuType,windowId,inventory,buf.readBlockPos())
    val access: ContainerLevelAccess = ContainerLevelAccess.create(inventory.player.level(), pos)
    val tile: SingleGuiMachineTile?
        get() = inventory.player.level().getBlockEntity(pos) as? SingleGuiMachineTile

    init {
        tile?.viewGuiPlayers?.add(inventory.player)
    }

    override fun removed(player: Player) {
        super.removed(player)
        tile?.viewGuiPlayers?.remove(player)
    }

    override fun quickMoveStack(player: Player, slot: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean {
        val block = DeferredSingleBlockRegister.getBlockRegistryObject(location)?.get() ?: return false
        return stillValid(access, player, block)
    }
}