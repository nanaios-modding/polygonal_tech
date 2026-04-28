package com.nanaios.polygonal_tech.lib.capability.fluid

import com.nanaios.polygonal_tech.lib.fluid.LongFluidStack
import com.nanaios.polygonal_tech.lib.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.lib.util.roundInt
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

interface ILongFluidHandler: IFluidHandler {
    fun getLongFluidInTank(tank: Int): LongFluidStack
    fun getTankLongCapacity(tank: Int): Long
    fun isFluidValid(tank: Int, stack: LongFluidStack): Boolean
    fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long
    fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack
    fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack

    override fun getFluidInTank(tank: Int) = getLongFluidInTank(tank).toFluidStack()
    override fun getTankCapacity(tank: Int) = getTankLongCapacity(tank).roundInt()
    override fun isFluidValid(tank: Int, stack: FluidStack) = isFluidValid(tank, stack.toLongFluidStack())
    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = fill(resource.toLongFluidStack(), action).roundInt()
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = drain(maxDrain.toLong(), action).toFluidStack()
    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = drain(resource.toLongFluidStack(), action).toFluidStack()
}