package com.nanaios.polygonal_tech.core.capability.item

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.item.ItemStack
import kotlin.math.min

open class ItemSlot(
    override val x: Int,
    override val y: Int,
    override var stack: ItemStack,
    protected val valid: (ItemStack) -> Boolean
) : IItemSlot {
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override var isDirty = false
        protected set
    override val slotLimit
        get() = 64

    protected fun getStackLimit(stack: ItemStack): Int {
        return min(slotLimit, stack.maxStackSize)
    }

    protected fun markDirty() {
        isDirty = true
        storage.onSyncValueChanged(this)
    }

    override fun insertItem(
        stack: ItemStack,
        simulate: Boolean
    ): ItemStack {
        if (stack.isEmpty) return ItemStack.EMPTY
        if (!valid(stack)) return stack

        val current = this.stack
        var limit = getStackLimit(stack)

        if (!current.isEmpty) {
            if (!ItemStack.isSameItemSameTags(stack, current)) return stack
            limit -= current.count
        }

        if (limit <= 0) return stack

        val reachedLimit = stack.count > limit

        if (!simulate) {
            if (current.isEmpty) {
                this.stack = if (reachedLimit) stack.copyWithCount(limit) else stack
            } else {
                current.grow(if (reachedLimit) limit else stack.count)
            }
            markDirty()
        }

        return if (reachedLimit) stack.copyWithCount(stack.count - limit) else ItemStack.EMPTY
    }

    override fun extractItem(amount: Int, simulate: Boolean): ItemStack {
        if (amount <= 0) return ItemStack.EMPTY

        val current = this.stack
        if (current.isEmpty) return ItemStack.EMPTY

        val toExtract = min(amount, current.maxStackSize)

        if (current.count <= toExtract) {
            if (!simulate) {
                this.stack = ItemStack.EMPTY
                markDirty()
                return current
            } else {
                return current.copy()
            }
        } else {
            if (!simulate) {
                this.stack = current.copyWithCount(current.count - toExtract)
                markDirty()
            }
            return current.copyWithCount(toExtract)
        }
    }

    override fun isItemValid(stack: ItemStack) = valid(stack)

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeItemStack(stack,true)
    }

    override fun readBuffer(buffer: FriendlyByteBuf) {
        stack = buffer.readItem()
    }

    override fun onSync() {
        isDirty = false
    }

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        stack.save(tag)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        stack = ItemStack.of(nbt)
    }
}