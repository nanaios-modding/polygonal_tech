package com.nanaios.polygonal_tech.core.capability.item

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandlerModifiable

/**
 * 複数の[IItemSlot]をグループ化して1つのインベントリの束とし、
 * Forgeにおける標準アイテム機能である[IItemHandlerModifiable]を通じて外部（パイプ、ホッパー等）へのアクセスを提供することを目的としたインターフェース。
 */
interface IItemSlotHandler: IItemHandlerModifiable {
    /** ハンドラーが管理・委譲対象としている全[IItemSlot]のリスト。 */
    val slots:List<IItemSlot>

    override fun isItemValid(slot: Int, stack: ItemStack) = slots[slot].isItemValid(stack)
    override fun getStackInSlot(slot: Int) = slots[slot].stack
    override fun setStackInSlot(slot: Int, stack: ItemStack) {
        slots[slot].stack = stack
    }
    override fun getSlots() = slots.size
    override fun getSlotLimit(slot: Int) = slots[slot].slotLimit
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = slots[slot].insertItem(stack, simulate)
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = slots[slot].extractItem(amount, simulate)
}