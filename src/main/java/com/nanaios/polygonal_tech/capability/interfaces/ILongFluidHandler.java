package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

/// long型の流体量を扱うためのインターフェース
public interface ILongFluidHandler extends IFluidHandler {
    /// タンク内の流体をLongFluidStackで取得します。
    ///
    /// @param tank タンクのインデックス
    /// @return タンク内の流体をLongFluidStackで返します。
    @Override
    @NotNull LongFluidStack getFluidInTank(int tank);

    /// タンクの最大容量をlong型で取得します。
    ///
    /// @param tank タンクのインデックス
    /// @return タンクの最大容量をlong型で返します。
    long getTankLongCapacity(int tank);

    /// 流体を抽出します。
    ///
    /// @param resource 抽出する流体の種類と量をLongFluidStackで指定します。
    /// @param action   抽出のシミュレーションを行うかどうかを指定します。
    /// @return 実際に抽出された流体の種類と量をLongFluidStackで返します。
    @NotNull LongFluidStack drainLong(LongFluidStack resource, FluidAction action);

    /// 流体を抽出します。
    ///
    /// @param maxDrain 抽出する流体の最大量をlong型で指定します。
    /// @param action   抽出のシミュレーションを行うかどうかを指定します。
    /// @return 実際に抽出された流体の種類と量をLongFluidStackで返します。
    @NotNull LongFluidStack drainLong(long maxDrain, FluidAction action);

    /// 流体を受け取ります。
    ///
    /// @param resource 受け取る流体の種類と量をLongFluidStackで指定します。
    /// @param action   受け取りのシミュレーションを行うかどうかを指定します。
    /// @return 実際に受け取った流体の量をlong型で返します。
    long fillLong(LongFluidStack resource, FluidAction action);


    @Override
    default int fill(FluidStack resource, FluidAction action) {
        return MathUtil.longToInt(fillLong(LongFluidStack.from(resource), action));
    }

    @Override
    @NotNull
    default LongFluidStack drain(FluidStack resource, FluidAction action) {
        return drainLong(LongFluidStack.from(resource), action);
    }

    @Override
    @NotNull
    default LongFluidStack drain(int maxDrain, FluidAction action) {
        return drainLong(maxDrain, action);
    }

    @Override
    default int getTankCapacity(int tank) {
        return MathUtil.longToInt(getTankLongCapacity(tank));
    }
}
