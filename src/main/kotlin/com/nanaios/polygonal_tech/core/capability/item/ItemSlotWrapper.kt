package com.nanaios.polygonal_tech.core.capability.item

import net.minecraft.world.item.ItemStack

class InputWrapperItemSlot(
    slot: IItemSlot
): IItemSlot by slot{
    override fun extractItem(amount: Int, simulate: Boolean): ItemStack {
        return ItemStack.EMPTY
    }
}

class OutputWrapperItemSlot(
    slot: IItemSlot
): IItemSlot by slot{
    override fun insertItem(stack: ItemStack, simulate: Boolean): ItemStack {
        return stack
    }
}