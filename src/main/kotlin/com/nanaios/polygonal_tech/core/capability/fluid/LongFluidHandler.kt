package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

class LongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank): this(tanks.toList())
}

class InputOnlyLongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank) : this(tanks.toList())

    override fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack = LongFluidStack.EMPTY
    override fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack = LongFluidStack.EMPTY
}

class OutputOnlyLongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank) : this(tanks.toList())

    override fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long = 0L
}