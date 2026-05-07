package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.core.util.roundInt
import net.minecraft.commands.arguments.ResourceArgument.resource
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

/**
 * 複数の[ILongFluidTank]を統括して操作し、外部（パイプ等）との大容量流体（[LongFluidStack]）の授受を
 * まとめて処理できるようにすることを目的としたCapabilityインターフェース。
 */
interface ILongFluidHandler: IFluidHandler {
    /** このハンドラーが管理している全タンクのリスト */
    val tanks: List<ILongFluidTank>

    /**
     * 所持している複数のタンクに対して順次流体の注入を試み、その合計注入量を返すことを目的とするメソッド。
     *
     * @param resource 注入したい流体
     * @param action 実際の更新かシミュレートかを示す[IFluidHandler.FluidAction]
     * @return 実際に注入された総量
     */
    fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long {
        var total = 0L
        tanks.forEach { tank ->
            val filled = tank.fill(resource,action)
            total += filled
            resource.shrink(filled)
        }
        return total
    }

    /**
     * 該当する流体の種類と量を指定して、保持しているタンク群から引き出すことを目的とするメソッド。
     * 内部的には要求量だけを指定する[drain]に委譲される。
     *
     * @param resource 引き出したい流体の種類と量を指定する[LongFluidStack]
     * @param action 実際の更新かシミュレートかを示す[IFluidHandler.FluidAction]
     * @return 引き出された流体と量のまとまり（[LongFluidStack]）
     */
    fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack = drain(resource.getAmount(), action)

    /**
     * 所持しているタンク群から順次探索し、指定された最大量まで同種の流体を引き出してまとめることを目的とするメソッド。
     *
     * @param maxDrain 引き出したい最大[Long]量
     * @param action 実際の更新かシミュレートかを示す[IFluidHandler.FluidAction]
     * @return 引き出された流体と量のまとまり（[LongFluidStack]）
     */
    fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack {
        var drained:LongFluidStack = LongFluidStack.EMPTY
        var want = maxDrain

        tanks.forEach { tank ->
            if(drained.isEmpty()){
                // 最初に引き出せるタンクを見つけて、流体を引き出す。
                drained = tank.drain(want, action)
                want -= drained.getAmount()
            } else if(tank.longFluid.isFluidEqual(drained)) {
                // すでに引き出した流体と同じ流体が入っているタンクを見つけたら、さらに引き出す。
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