package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import com.nanaios.polygonal_tech.util.interfaces.IEventType;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

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

    @Override
    public boolean canInput() {
        return false;
    }

    @Override
    public boolean canOutput() {
        return false;
    }
}
