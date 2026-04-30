package com.nanaios.polygonal_tech.lib.capability.fluid

import com.nanaios.polygonal_tech.lib.capability.ICapability
import com.nanaios.polygonal_tech.lib.fluid.LongFluidStack
import com.nanaios.polygonal_tech.lib.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.lib.util.roundInt
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction

interface ILongFluidTank: IFluidTank, ICapability {
    val longFluid: LongFluidStack
    val longFluidAmount: Long
    val longCapability: Long
    fun isFluidValid(stack: LongFluidStack): Boolean
    fun fillLong(resource: LongFluidStack, action: FluidAction): Long
    fun drainLong(maxDrain: Long, action: FluidAction): LongFluidStack
    fun drainLong(resource: LongFluidStack, action: FluidAction): LongFluidStack

    override fun getFluid() = longFluid.toFluidStack()
    override fun getFluidAmount() = longFluidAmount.roundInt()
    override fun getCapacity() = longCapability.roundInt()
    override fun isFluidValid(stack: FluidStack) = isFluidValid(stack.toLongFluidStack())
    override fun fill(resource: FluidStack, action: FluidAction) = fillLong(resource.toLongFluidStack(), action).roundInt()
    override fun drain(maxDrain: Int, action: FluidAction) = drainLong(maxDrain.toLong(), action).toFluidStack()
    override fun drain(resource: FluidStack, action: FluidAction) = drainLong(resource.toLongFluidStack(), action).toFluidStack()
}