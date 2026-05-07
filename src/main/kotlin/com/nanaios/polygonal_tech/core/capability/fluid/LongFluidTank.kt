package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.fluid.readLongFluidStack
import com.nanaios.polygonal_tech.core.fluid.writeLongFluidStack
import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

/**
 * [ILongFluidTank]の標準的な実装クラスであり、
 * NBTへのセーブ/ロード、ネットワーク同期（[ISyncValue]としての振る舞い）、大容量流体のシミュレート及び実更新ロジックを統合提供することを目的とする。
 *
 * @param fluid 初期の流体内容
 * @param amount 初期の流体量
 * @param capacity このタンクの最大容量
 * @param valid 注入されようとしている流体が条件に合うか（熱量のみ、水のみ等）を検証する述語関数
 */
open class LongFluidTank(
    fluid: LongFluidStack,
    amount: Long,
    capacity: Long,
    protected val valid: (LongFluidStack) -> Boolean
) : ILongFluidTank {
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override var isDirty = false
        protected set
    override var longFluid = fluid
        protected set
    override val longFluidAmount
        get() = longFluid.getAmount()
    override var longCapability = capacity
        protected set

    protected fun markDirty() {
        isDirty = true
        storage.onSyncValueChanged(this)
    }

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        longFluid.writeToNBT(tag)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        longFluid = LongFluidStack.loadFluidStackFromNBT(nbt)
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeLongFluidStack(longFluid)
        buffer.writeLong(longCapability)
    }

    override fun readBuffer(buffer: FriendlyByteBuf) {
        longFluid = buffer.readLongFluidStack()
        longCapability = buffer.readLong()
    }

    override fun onSync() {
        isDirty = false
    }

    override fun isFluidValid(stack: LongFluidStack): Boolean {
        return valid(stack)
    }

    override fun fill(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): Long {
        if (resource.isEmpty() || !isFluidValid(resource)) {
            return 0
        }
        if (action.simulate()) {
            if (longFluid.isEmpty()) {
                return min(longCapability, resource.getAmount())
            }
            if (!longFluid.isFluidEqual(resource)) {
                return 0
            }
            return min(longCapability - longFluid.getAmount(), resource.getAmount())
        }
        if (longFluid.isEmpty()) {
            longFluid = LongFluidStack(resource.fluid, min(longCapability, resource.getAmount()))
            markDirty()
            return longFluid.getAmount()
        }
        if (!longFluid.isFluidEqual(resource)) {
            return 0
        }
        var filled = longCapability - longFluid.getAmount()

        if (resource.getAmount() < filled) {
            longFluid.grow(resource.getAmount())
            filled = resource.getAmount()
        } else {
            fluid.setAmount(capacity)
        }
        if (filled > 0) markDirty()
        return filled
    }

    override fun drain(
        maxDrain: Long,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        var drained = maxDrain
        if (longFluid.getAmount() < drained) {
            drained = longFluid.getAmount()
        }
        val stack = LongFluidStack(longFluid.fluid, drained)
        if (action.execute() && drained > 0) {
            longFluid.shrink(drained)
            markDirty()
        }
        return stack
    }

    override fun drain(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
            return LongFluidStack.EMPTY
        }
        return drain(resource.getAmount(), action)
    }
}