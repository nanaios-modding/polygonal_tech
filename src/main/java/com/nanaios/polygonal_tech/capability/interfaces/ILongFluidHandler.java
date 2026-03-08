package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface ILongFluidHandler extends IFluidHandler {
    long getLongFluidAmount();

    long getLongTankCapacity();

    long fillLongFluid(LongFluidStack resource, boolean simulate);

    LongFluidStack drainLongFluid(LongFluidStack resource, boolean simulate);

    LongFluidStack drainLongFluid(long maxDrain, boolean simulate);
}
