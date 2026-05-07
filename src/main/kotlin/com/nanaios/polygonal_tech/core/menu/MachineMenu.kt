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
import net.minecraft.world.inventory.Slot
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

    protected open val inventoryYOffset = 84
    protected open val inventoryXOffset = 8
    protected open var itemSlotCount: Int = 0

    init {
        addPlayerInventory(inventory)
        addPlayerHotbar(inventory)


        tile?.let { machine ->
            machine.viewGuiPlayers.add(inventory.player)
            machine.sendSyncGuiPacket()
        }
    }

    override fun removed(player: Player) {
        super.removed(player)
        tile?.viewGuiPlayers?.remove(player)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot = this.slots[index]

        if (!slot.hasItem()) {
            return ItemStack.EMPTY
        }

        val stack = slot.item
        val copy = stack.copy()

        if (index < itemSlotCount) {
            // machine -> player
            if (!moveItemStackTo(stack, itemSlotCount, slots.size, true)) {
                return ItemStack.EMPTY
            }
        } else {
            // player -> machine
            if (!moveItemStackTo(stack, 0, itemSlotCount, false)) {
                return ItemStack.EMPTY
            }
        }

        if (stack.isEmpty) {
            slot.set(ItemStack.EMPTY)
        } else {
            slot.setChanged()
        }

        return copy
    }

    override fun stillValid(player: Player): Boolean {
        val block = DeferredSingleBlockRegister.getBlockRegistryObject(location)?.get() ?: return false
        return stillValid(access, player, block)
    }

    private fun addPlayerInventory(playerInv: Inventory) {
        for (row in 0..2) {
            for (col in 0..8) {
                val index = col + row * 9 + 9

                val x = inventoryXOffset + col * 18
                val y = inventoryYOffset + row * 18

                this.addSlot(Slot(playerInv, index, x, y))
            }
        }
    }

    private fun addPlayerHotbar(playerInv: Inventory) {
        val yOffset: Int = inventoryYOffset + 58

        for (col in 0..8) {
            val x = inventoryXOffset + col * 18

            this.addSlot(Slot(playerInv, col, x, yOffset))
        }
    }
}