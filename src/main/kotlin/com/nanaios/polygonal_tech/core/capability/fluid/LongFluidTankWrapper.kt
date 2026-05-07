package com.nanaios.polygonal_tech.core.capability.fluid

import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

/**
 * 既存の[ILongFluidTank]に対する「抽出」をブロックし、「入力（搬入）専用」のタンクとして振る舞わせることを目的としたラッパークラス。
 */
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

/**
 * 既存の[ILongFluidTank]に対する「注入」をブロックし、「出力（搬出）専用」のタンクとして振る舞わせることを目的としたラッパークラス。
 */
class OutputOnlyLongFluidTankWrapper(
    tank: ILongFluidTank
): ILongFluidTank by tank {
    override fun fill(resource: LongFluidStack, action: IFluidHandler.FluidAction): Long {
        return 0L
    }
}