package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public interface ILongFluidTank extends IFluidTank, IFluidHandler {
    long getFluidLongAmount();

    long getLongCapacity();

    long fillLong(LongFluidStack resource, FluidAction action);

    @NotNull LongFluidStack getFluid();

    LongFluidStack drain(long maxDrain, FluidAction action);

    default int getTanks() {
        return 1;
    }

    default int getFluidAmount() {
        return MathUtil.longToInt(getFluidLongAmount());
    }

    @NotNull LongFluidStack getFluidInTank(int tank);


    @Override
    default int getTankCapacity(int tank) {
        return getCapacity();
    }

    default int getCapacity() {
        return MathUtil.longToInt(getLongCapacity());
    }

    @Override
    default boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return isFluidValid(stack);
    }

    @Override
    default int fill(FluidStack resource, FluidAction action) {
        return MathUtil.longToInt(fillLong(LongFluidStack.of(resource), action));
    }

    @Override
    default @NotNull LongFluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(getFluid())) return LongFluidStack.EMPTY;
        return drain((long) resource.getAmount(), action);
    }

    default @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(getFluid())) return LongFluidStack.EMPTY;
        return drain(resource.getLongAmount(), action);
    }

    @Override
    @NotNull
    default LongFluidStack drain(int maxDrain, FluidAction action) {
        return drain((long) maxDrain, action);
    }
}
