package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

class InputOnlyLongFluidTankWrapper(
    tank: ILongFluidTank
): ILongFluidTank by tank {
    override fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack {
        return LongFluidStack.EMPTY
    }

    override fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack {
        return LongFluidStack.EMPTY
    }
}

class OutputOnlyLongFluidTankWrapper(
    tank: ILongFluidTank
): ILongFluidTank by tank {
    override fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long {
        return 0L
    }
}