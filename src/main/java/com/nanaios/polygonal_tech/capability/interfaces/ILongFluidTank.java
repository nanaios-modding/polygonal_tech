package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.jetbrains.annotations.NotNull;

public interface ILongFluidTank extends IFluidTank {
    long getFluidLongAmount();

    long getLongCapacity();

    long fillLong(LongFluidStack resource, FluidAction action);

    @Override
    @NotNull LongFluidStack getFluid();

    LongFluidStack drain(long maxDrain, FluidAction action);

    @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action);


    @NotNull
    default LongFluidStack drain(int maxDrain, FluidAction action) {
        return drain((long) maxDrain, action);
    }

    default int getFluidAmount() {
        return MathUtil.longToInt(getFluidLongAmount());
    }

    @Override
    @NotNull
    default LongFluidStack drain(FluidStack resource, FluidAction action) {
        return drain(LongFluidStack.from(resource), action);
    }

    @Override
    default int getCapacity() {
        return MathUtil.longToInt(getLongCapacity());
    }

    @Override
    default int fill(FluidStack resource, FluidAction action) {
        return MathUtil.longToInt(fillLong(LongFluidStack.from(resource), action));
    }
}
