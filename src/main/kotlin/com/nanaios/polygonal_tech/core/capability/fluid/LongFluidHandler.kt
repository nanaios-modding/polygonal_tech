package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

/**
 * 複数の[ILongFluidTank]をまとめ、単一の[ILongFluidHandler]として振舞うことを目的とした実装クラス。
 * アイテムのインベントリのように、複数の流体タンクに対する透過的なアクセスを提供する。
 *
 * @param tanks 管理・委譲対象となるタンクのリスト
 */
class LongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank): this(tanks.toList())
}

/**
 * 複数のタンクをまとめた上で、外部からの「注入（搬入）」のみを許可することを目的とするラッパークラス。
 */
class InputOnlyLongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank) : this(tanks.toList())

    /** 搬出を防ぐため、常に空の[LongFluidStack]を返す。 */
    override fun drain(maxDrain: Long, action: IFluidHandler.FluidAction): LongFluidStack = LongFluidStack.EMPTY
    /** 搬出を防ぐため、常に空の[LongFluidStack]を返す。 */
    override fun drain(resource: LongFluidStack, action: IFluidHandler.FluidAction): LongFluidStack = LongFluidStack.EMPTY
}

/**
 * 複数のタンクをまとめた上で、外部からの「抽出（搬出）」のみを許可することを目的とするラッパークラス。
 */
class OutputOnlyLongFluidHandler(override val tanks: List<ILongFluidTank>) : ILongFluidHandler {
    constructor(vararg tanks: ILongFluidTank) : this(tanks.toList())

    /** 搬入を防ぐため、常に0を返す。 */
    override fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long = 0L
}