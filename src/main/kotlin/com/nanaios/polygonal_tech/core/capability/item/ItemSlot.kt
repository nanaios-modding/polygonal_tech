package com.nanaios.polygonal_tech.core.capability.item

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.ItemStackHandler
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
        get() = stack.maxStackSize

    protected fun markDirty() {
        isDirty = true
        storage.onSyncValueChanged(this)
    }

    override fun insertItem(
        stack: ItemStack,
        simulate: Boolean
    ): ItemStack {
        if(stack.isEmpty) return ItemStack.EMPTY
        if(!valid(stack)) return stack

        if(this.stack.isEmpty) {
            if(!simulate) {
                this.stack = stack.copy()
                markDirty()
            }
            return ItemStack.EMPTY
        }

        if(stack.equals(this.stack,false)) {
            val grow = min(stack.count, slotLimit - this.stack.count)
            if(grow == 0) return stack

            val copy = stack.copy()
            copy.shrink(grow)

            if(!simulate) {
                this.stack.grow(grow)
                markDirty()
            }
            return copy
        }

        return stack
    }

    override fun extractItem(amount: Int, simulate: Boolean): ItemStack {
        TODO("Not yet implemented")
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