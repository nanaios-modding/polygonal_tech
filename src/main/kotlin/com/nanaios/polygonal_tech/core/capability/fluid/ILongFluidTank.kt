package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.fluid.toLongFluidStack
import com.nanaios.polygonal_tech.core.util.roundInt
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction

/**
 * 基本的な[IFluidTank]（流体タンク）のインターフェースを拡張し、
 * [Int.MAX_VALUE]を超える大容量の流体（[LongFluidStack]）を直接管理・操作し、
 * かつネットワーク同期（[ICapability]）に対応させることを目的としたインターフェース。
 */
interface ILongFluidTank: IFluidTank, ICapability {
    /** タンク内で現在保持している大容量流体データ */
    val longFluid: LongFluidStack
    /** 現在保持している流体の[Long]型数量 */
    val longFluidAmount: Long
    /** このタンクが保持できる流体の最大容量（[Long]型） */
    val longCapability: Long

    /**
     * 投入されようとしている大容量流体（[LongFluidStack]）が、このタンクの受け入れ条件を満たしているかを判定することを目的とする。
     *
     * @param stack 判定・シミュレート対象の流体
     * @return 受け入れ可能なら[true]
     */
    fun isFluidValid(stack: LongFluidStack): Boolean

    /**
     * 大容量流体を受け入れてタンクに充填することを目的とするメソッド。
     *
     * @param resource 充填しようとする流体
     * @param action 実際の充填を行うか、シミュレートかで動作を分ける[FluidAction]
     * @return 実際に受け入れた（または受け入れ可能な）[Long]型の流体量
     */
    fun fill(resource: LongFluidStack, action: FluidAction): Long

    /**
     * タンク内から指定した最大[Long]量の流体を抽出することを目的とするメソッド。
     *
     * @param maxDrain 抽出したい最大流体量
     * @param action 実際の抽出を行うか、シミュレートかで動作を分ける[FluidAction]
     * @return 抽出された流体情報を持つ[LongFluidStack]
     */
    fun drain(maxDrain: Long, action: FluidAction): LongFluidStack

    /**
     * 特定の流体の種類と量を指定してタンク内から抽出することを目的とするメソッド。
     *
     * @param resource 抽出したい流体の条件を指定する[LongFluidStack]
     * @param action シミュレートか実行かを示す[FluidAction]
     * @return 抽出された[LongFluidStack]
     */
    fun drain(resource: LongFluidStack, action: FluidAction): LongFluidStack

    override fun getFluid() = longFluid.toFluidStack()
    override fun getFluidAmount() = longFluidAmount.roundInt()
    override fun getCapacity() = longCapability.roundInt()
    override fun isFluidValid(stack: FluidStack) = isFluidValid(stack.toLongFluidStack())
    override fun fill(resource: FluidStack, action: FluidAction) = fill(resource.toLongFluidStack(), action).roundInt()
    override fun drain(maxDrain: Int, action: FluidAction) = drain(maxDrain.toLong(), action).toFluidStack()
    override fun drain(resource: FluidStack, action: FluidAction) = drain(resource.toLongFluidStack(), action).toFluidStack()
}