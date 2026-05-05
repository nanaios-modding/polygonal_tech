package com.nanaios.polygonal_tech.core.capability.item

import net.minecraft.world.item.ItemStack

class ItemSlotHandler(override val slots: List<IItemSlot> ): IItemSlotHandler {
    constructor(vararg slots: IItemSlot): this(slots.toList())
}

class InputOnlyItemSlotHandler(override val slots: List<IItemSlot>): IItemSlotHandler {
    constructor(vararg slots: IItemSlot) : this(slots.toList())

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack = ItemStack.EMPTY
}

class OutputOnlyItemSlotHandler(override val slots: List<IItemSlot>): IItemSlotHandler {
    constructor(vararg slots: IItemSlot) : this(slots.toList())

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack = stack
}