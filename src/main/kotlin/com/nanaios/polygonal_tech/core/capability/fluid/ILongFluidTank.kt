package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.core.util.roundInt
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction

interface ILongFluidTank: IFluidTank, ICapability {
    val longFluid: LongFluidStack
    val longFluidAmount: Long
    val longCapability: Long
    fun isFluidValid(stack: LongFluidStack): Boolean
    fun fill(resource: LongFluidStack, action: FluidAction): Long
    fun drain(maxDrain: Long, action: FluidAction): LongFluidStack
    fun drain(resource: LongFluidStack, action: FluidAction): LongFluidStack

    override fun getFluid() = longFluid.toFluidStack()
    override fun getFluidAmount() = longFluidAmount.roundInt()
    override fun getCapacity() = longCapability.roundInt()
    override fun isFluidValid(stack: FluidStack) = isFluidValid(stack.toLongFluidStack())
    override fun fill(resource: FluidStack, action: FluidAction) = fill(resource.toLongFluidStack(), action).roundInt()
    override fun drain(maxDrain: Int, action: FluidAction) = drain(maxDrain.toLong(), action).toFluidStack()
    override fun drain(resource: FluidStack, action: FluidAction) = drain(resource.toLongFluidStack(), action).toFluidStack()
}