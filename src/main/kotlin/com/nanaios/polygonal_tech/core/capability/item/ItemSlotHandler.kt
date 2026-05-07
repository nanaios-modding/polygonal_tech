package com.nanaios.polygonal_tech.core.capability.item

import net.minecraft.world.item.ItemStack

/**
 * [IItemSlot]をリストや可変長引数から受け取り、[IItemSlotHandler]として操作可能にすることを目的とする実装クラス。
 *
 * @param slots 管理対象となるスロット群
 */
class ItemSlotHandler(override val slots: List<IItemSlot> ): IItemSlotHandler {
    constructor(vararg slots: IItemSlot): this(slots.toList())
}

/**
 * ホッパー等の外部からの「アイテム抽出」をブロックし、搬入のみを許可することを目的としたラッパークラス。
 */
class InputOnlyItemSlotHandler(override val slots: List<IItemSlot>): IItemSlotHandler {
    constructor(vararg slots: IItemSlot) : this(slots.toList())

    /** 搬出機能を使えなくするため、常に空の[ItemStack]を返す。 */
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack = ItemStack.EMPTY
}

/**
 * ホッパー等の外部からの「アイテム搬入」をブロックし、抽出のみを許可することを目的としたラッパークラス。
 */
class OutputOnlyItemSlotHandler(override val slots: List<IItemSlot>): IItemSlotHandler {
    constructor(vararg slots: IItemSlot) : this(slots.toList())

    /** 搬入機能を使えなくするため、渡された[ItemStack]をそのまま返す（受け入れ不可と見なされる）。 */
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack = stack
}