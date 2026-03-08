package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidHandler;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class LongFluidHandler implements ILongFluidHandler {
    @Override
    public long getLongFluidAmount() {
        return 0;
    }

    @Override
    public long getLongTankCapacity() {
        return 0;
    }

    @Override
    public long fillLongFluid(LongFluidStack resource, boolean simulate) {
        return 0;
    }

    @Override
    public LongFluidStack drainLongFluid(LongFluidStack resource, boolean simulate) {
        return null;
    }

    @Override
    public LongFluidStack drainLongFluid(long maxDrain, boolean simulate) {
        return null;
    }

    @Override
    public int getTanks() {
        return 0;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return null;
    }

    @Override
    public int getTankCapacity(int i) {
        return 0;
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return false;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        return 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return null;
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        return null;
    }
}
