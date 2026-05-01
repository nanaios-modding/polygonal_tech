package com.nanaios.polygonal_tech.lib.capability.fluid

import com.nanaios.polygonal_tech.lib.fluid.LongFluidStack
import com.nanaios.polygonal_tech.lib.fluid.readLongFluidStack
import com.nanaios.polygonal_tech.lib.fluid.writeLongFluidStack
import com.nanaios.polygonal_tech.lib.util.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncType
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

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

    override fun fillLong(
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
            storage.onSyncValueChanged(this)
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
        if (filled > 0) storage.onSyncValueChanged(this)
        return filled
    }

    override fun drainLong(
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
            storage.onSyncValueChanged(this)
        }
        return stack
    }

    override fun drainLong(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
            return LongFluidStack.EMPTY
        }
        return drainLong(resource.getAmount(), action)
    }
}

class InputOnlyLongFluidTankWrapper(
    tank: ILongFluidTank
): ILongFluidTank by tank {
    override fun drainLong(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack {
        return LongFluidStack.EMPTY
    }

    override fun drainLong(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack {
        return LongFluidStack.EMPTY
    }
}

class OutputOnlyLongFluidTankWrapper(
    tank: ILongFluidTank
): ILongFluidTank by tank {
    override fun fillLong(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long {
        return 0L
    }
}