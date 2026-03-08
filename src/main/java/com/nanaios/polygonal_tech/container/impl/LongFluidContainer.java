package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.impl.EmptyLongFluidTank;
import com.nanaios.polygonal_tech.capability.impl.LongFluidTank;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.LongSupplier;
import java.util.function.Predicate;

public class LongFluidContainer extends BaseContainer<ILongFluidTank> {
    public LongFluidContainer(ILongFluidTank input, ILongFluidTank output, ILongFluidTank defaultValue) {
        super(input, output, defaultValue);
    }

    public static LongFluidContainer create(LongSupplier capacity, Predicate<FluidStack> validator) {
        LongFluidTank base = new LongFluidTank(capacity, validator);
        return new LongFluidContainer(
                new LongFluidTank.InputOnly(base),
                new LongFluidTank.OutputOnly(base),
                EmptyLongFluidTank.INSTANCE
        );
    }
}
