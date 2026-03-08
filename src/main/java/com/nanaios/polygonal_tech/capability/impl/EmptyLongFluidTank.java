package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

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
    public long fillLong(LongFluidStack resource, FluidAction action) {
        return 0;
    }

    @Override
    public @NotNull LongFluidStack getFluid() {
        return LongFluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return false;
    }

    @Override
    public LongFluidStack drain(long maxDrain, FluidAction action) {
        return LongFluidStack.EMPTY;
    }

    @Override
    public int getTanks() {
        return 0;
    }

    @Override
    public @NotNull LongFluidStack getFluidInTank(int tank) {
        return LongFluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return false;
    }

    @Override
    public @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        return LongFluidStack.EMPTY;
    }

    @Override
    public @NotNull LongFluidStack drain(int maxDrain, FluidAction action) {
        return LongFluidStack.EMPTY;
    }
}
