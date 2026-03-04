package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface ILongFluidHandler extends IFluidHandler,ICapabilityMarker {
    long getLongFluidAmount();

    long getLongTankCapacity();

    long fillLongFluid(FluidStack resource, boolean simulate);

    FluidStack drainLongFluid(FluidStack resource, boolean simulate);

    FluidStack drainLongFluid(long maxDrain, boolean simulate);
}
