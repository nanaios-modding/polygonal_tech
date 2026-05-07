package com.nanaios.polygonal_tech.core.capability.item

import com.nanaios.polygonal_tech.core.capability.ICapability
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandlerModifiable

/**
 * 独自のインベントリを持つ機械等において、GUI上の表示位置（[x], [y]）を関連付けた1スロット分のアイテム情報を保持し、
 * ネットワーク同期等の[ICapability]としての特性を持たせることを目的としたインターフェース。
 */
interface IItemSlot: IItemHandlerModifiable, ICapability {
    /** GUI上でこのスロットが配置されるべきX座標 */
    val x:Int
    /** GUI上でこのスロットが配置されるべきY座標 */
    val y:Int
    /** 現在このスロットに入っている[ItemStack] */
    var stack: ItemStack
    /** このスロットが保持できるアイテムの最大数 */
    val slotLimit: Int

    /**
     * スロットにアイテムを搬入することを目的とするメソッド。
     *
     * @param stack 搬入したい[ItemStack]
     * @param simulate 実際の搬入を行うか、シミュレートかを示すフラグ
     * @return 搬入しきれず余った[ItemStack]。全て入った場合は[ItemStack.EMPTY]を返す。
     */
    fun insertItem(stack: ItemStack, simulate: Boolean): ItemStack

    /**
     * スロットからアイテムを搬出することを目的とするメソッド。
     *
     * @param amount 搬出したい最大数量
     * @param simulate 実際の搬出を行うか、シミュレートかを示すフラグ
     * @return 実際に搬出された[ItemStack]
     */
    fun extractItem(amount: Int, simulate: Boolean): ItemStack

    /**
     * 対象のアイテムがこのスロットの条件を満たしており、搬入可能かを検証することを目的とするメソッド。
     *
     * @param stack 検証対象の[ItemStack]
     * @return 搬入可能であれば[true]
     */
    fun isItemValid(stack: ItemStack): Boolean

    override fun getSlots() = 1
    override fun getSlotLimit(slot: Int) = slotLimit
    override fun getStackInSlot(slot: Int) = stack
    override fun setStackInSlot(slot: Int, stack: ItemStack) { this.stack = stack }
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = insertItem(stack, simulate)
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = extractItem(amount, simulate)
    override fun isItemValid(slot: Int, stack: ItemStack) = isItemValid(stack)
}