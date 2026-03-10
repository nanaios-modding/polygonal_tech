package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public interface ILongFluidHandler extends IFluidHandler{
    @Override
    @NotNull LongFluidStack getFluidInTank(int tank);
    long getTankLongCapacity(int tank);
    @NotNull LongFluidStack drainLong(LongFluidStack resource, FluidAction action);
    @NotNull LongFluidStack drainLong(long maxDrain, FluidAction action);
    long fillLong(LongFluidStack resource, FluidAction action);


    @Override
    default int fill(FluidStack resource, FluidAction action) {
        return MathUtil.longToInt(fillLong(LongFluidStack.from(resource), action));
    }

    @Override
    default @NotNull LongFluidStack drain(FluidStack resource, FluidAction action) {
        return drainLong(LongFluidStack.from(resource), action);
    }

    @Override
    default @NotNull LongFluidStack drain(int maxDrain, FluidAction action) {
        return drainLong(maxDrain, action);
    }

    @Override
    default int getTankCapacity(int tank) {
        return MathUtil.longToInt(getTankLongCapacity(tank));
    }
}
