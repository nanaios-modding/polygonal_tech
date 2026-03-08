package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.LongSupplier;
import java.util.function.Predicate;

public class LongFluidTank implements ILongFluidTank {
    protected final LongSupplier capacity;
    protected final Predicate<FluidStack> validator;
    protected LongFluidStack fluid = LongFluidStack.EMPTY;

    public LongFluidTank(LongSupplier capacity, Predicate<FluidStack> validator) {
        this.capacity = capacity;
        this.validator = validator;
    }

    @Override
    public long getFluidLongAmount() {
        return fluid.getAmount();
    }

    @Override
    public long getLongCapacity() {
        return capacity.getAsLong();
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) return 0;

        if (action.simulate()) {
            if (fluid.isEmpty()) return Math.min(capacity.getAsLong(), resource.getAmount());
            if (!fluid.isFluidEqual(resource)) return 0;
            return Math.min(capacity.getAsLong() - fluid.getAmount(), resource.getAmount());
        }

        if (fluid.isEmpty()) {
            fluid = new LongFluidStack(resource, Math.min(capacity.getAsLong(), resource.getAmount()));
            onContentsChanged();
            return fluid.getLongAmount();
        }

        if (!fluid.isFluidEqual(resource)) return 0;

        long filled = capacity.getAsLong() - fluid.getLongAmount();

        if (resource.getAmount() < filled) {
            fluid.grow(resource.getLongAmount());
            filled = resource.getLongAmount();
        } else {
            fluid.setAmount(capacity.getAsLong());
        }

        if (filled > 0) onContentsChanged();
        return filled;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    public @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(getFluid())) return LongFluidStack.EMPTY;
        return drain(resource.getLongAmount(), action);
    }

    @Override
    @NotNull
    public LongFluidStack drain(int maxDrain, FluidAction action) {
        return drain((long) maxDrain, action);
    }


    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return isFluidValid(stack);
    }

    @Override
    public @NotNull LongFluidStack getFluid() {
        return fluid;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return validator.test(stack);
    }

    @Override
    public @NotNull LongFluidStack getFluidInTank(int tank) {
        return fluid;
    }

    @Override
    public LongFluidStack drain(long maxDrain, FluidAction action) {

        long drained = maxDrain;
        if (fluid.getLongAmount() < drained) {
            drained = fluid.getLongAmount();
        }

        LongFluidStack stack = new LongFluidStack(fluid, drained);
        if (action.execute() && drained > 0) {
            fluid.shrink(drained);
            onContentsChanged();
        }

        return stack;
    }

    protected void onContentsChanged() {

    }

    public void setFluid(LongFluidStack stack)
    {
        this.fluid = stack;
    }

    public boolean isEmpty()
    {
        return fluid.isEmpty();
    }

    public long getSpace()
    {
        return Math.max(0, capacity.getAsLong() - fluid.getAmount());
    }

    public static class InputOnly extends LongFluidTank {
        public InputOnly(LongFluidTank base) {
            super(base.capacity, base.validator);
            this.fluid = base.fluid;
        }

        @Override
        public LongFluidStack drain(long maxDrain, FluidAction action) {
            return LongFluidStack.EMPTY;
        }
    }

    public static class OutputOnly extends LongFluidTank {
        public OutputOnly(LongFluidTank base) {
            super(base.capacity, base.validator);
            this.fluid = base.fluid;
        }

        @Override
        public long fillLong(LongFluidStack resource, FluidAction action) {
            return 0;
        }
    }
}
