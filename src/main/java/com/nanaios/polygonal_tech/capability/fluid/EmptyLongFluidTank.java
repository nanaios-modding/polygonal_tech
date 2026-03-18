package com.nanaios.polygonal_tech.capability.fluid;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.jetbrains.annotations.NotNull;

/// ILongFluidTankの空実装。常に空の流体スタックを返し、流体の挿入や抽出を受け付けません。
/// ifPresentのデフォルト値として使用できます。
/// 例えば、流体タンクが存在しない場合にこのクラスのインスタンスを返すことで、呼び出し側はnullチェックをせずに安全に流体タンクを操作できます。
/// このクラスはシングルトンパターンで実装されており、INSTANCEフィールドを通じて唯一のインスタンスにアクセスできます。
public class EmptyLongFluidTank implements ILongFluidTank {
    public static final EmptyLongFluidTank INSTANCE = new EmptyLongFluidTank();

    @Override
    public long getFluidLongAmount() {
        return 0;
    }

    @Override
    public long getLongCapacity() {
        return 0;
    }

    @Override
    public void setFluid(LongFluidStack fluidStack) {

    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        return 0;
    }

    @Override
    @NotNull
    public LongFluidStack getFluid() {
        return LongFluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return false;
    }

    @Override
    @NotNull
    public LongFluidStack drain(long maxDrain, FluidAction action) {
        return LongFluidStack.EMPTY;
    }

    @Override
    @NotNull
    public LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        return LongFluidStack.EMPTY;
    }
}
