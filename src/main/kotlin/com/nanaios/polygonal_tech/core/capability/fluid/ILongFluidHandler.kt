package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.core.util.roundInt
import net.minecraft.commands.arguments.ResourceArgument.resource
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

interface ILongFluidHandler: IFluidHandler {
    val tanks: List<ILongFluidTank>

    fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long {
        var total = 0L
        tanks.forEach { tank ->
            val filled = tank.fill(resource,action)
            total += filled
            resource.shrink(filled)
        }
        return total
    }
    fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack = drain(resource.getAmount(), action)

    fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack {
        var drained:LongFluidStack = LongFluidStack.EMPTY
        var want = maxDrain

        tanks.forEach { tank ->
            if(drained.isEmpty()){
                // 最初に引き出せるタンクを見つけて、液体を引き出す
                drained = tank.drain(want, action)
                want -= drained.getAmount()
            } else if(tank.longFluid.isFluidEqual(drained)) {
                // すでに引き出した液体と同じ液体が入っているタンクを見つけたら、さらに引き出す
                val additional = tank.drain(want, action).getAmount()
                drained.grow(additional)
                want -= additional
            }
        }

        return drained
    }

    fun isFluidValid(tank: Int, stack: LongFluidStack): Boolean = tanks[tank].isFluidValid(stack)
    fun getLongFluidInTank(tank: Int): LongFluidStack = tanks[tank].longFluid
    fun getTankLongCapacity(tank: Int): Long = tanks[tank].longCapability

    override fun getTanks(): Int = tanks.size
    override fun getFluidInTank(tank: Int) = getLongFluidInTank(tank).toFluidStack()
    override fun getTankCapacity(tank: Int) = getTankLongCapacity(tank).roundInt()
    override fun isFluidValid(tank: Int, stack: FluidStack) = isFluidValid(tank, stack.toLongFluidStack())
    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = fill(resource.toLongFluidStack(), action).roundInt()
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = drain(maxDrain.toLong(), action).toFluidStack()
    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = drain(resource.toLongFluidStack(), action).toFluidStack()
}