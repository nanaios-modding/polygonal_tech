package com.nanaios.polygonal_tech.lib.capability.fluid

import com.nanaios.polygonal_tech.lib.fluid.LongFluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class LongFluidHandler(
    capacity: Long
):ILongFluidHandler {
    override fun getLongFluidInTank(tank: Int): LongFluidStack {
        TODO("Not yet implemented")
    }

    override fun getTankLongCapacity(tank: Int): Long {
        TODO("Not yet implemented")
    }

    override fun isFluidValid(
        tank: Int,
        stack: LongFluidStack
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun fill(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): Long {
        TODO("Not yet implemented")
    }

    override fun drain(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        TODO("Not yet implemented")
    }

    override fun drain(
        maxDrain: Long,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        TODO("Not yet implemented")
    }

    override fun getTanks(): Int {
        TODO("Not yet implemented")
    }
}

class InputOnlyLongFluidHandler(
    handler:ILongFluidHandler,
): ILongFluidHandler by handler {
    override fun drain(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        return LongFluidStack.EMPTY
    }

    override fun drain(
        maxDrain: Long,
        action: IFluidHandler.FluidAction
    ): LongFluidStack {
        return LongFluidStack.EMPTY
    }
}

class OutputOnlyLongFluidHandler(
    handler:ILongFluidHandler,
): ILongFluidHandler by handler {
    override fun fill(
        resource: LongFluidStack,
        action: IFluidHandler.FluidAction
    ): Long {
        return 0L
    }
}