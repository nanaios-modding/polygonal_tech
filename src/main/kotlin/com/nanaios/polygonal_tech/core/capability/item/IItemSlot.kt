package com.nanaios.polygonal_tech.core.capability.item

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandlerModifiable

interface IItemSlot: IItemHandlerModifiable {
    val x:Int
    val y:Int
    var stack: ItemStack
    val slotLimit: Int

    fun insertItem(stack: ItemStack, simulate: Boolean): ItemStack
    fun extractItem(amount: Int, simulate: Boolean): ItemStack
    fun isItemValid(stack: ItemStack): Boolean

    override fun getSlots() = 1
    override fun getSlotLimit(slot: Int) = slotLimit
    override fun getStackInSlot(slot: Int) = stack
    override fun setStackInSlot(slot: Int, stack: ItemStack) { this.stack = stack }
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = insertItem(stack, simulate)
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = extractItem(amount, simulate)
    override fun isItemValid(slot: Int, stack: ItemStack) = isItemValid(stack)
}