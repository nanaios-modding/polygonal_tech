package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
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

    public @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(getFluid())) return LongFluidStack.EMPTY;
        return drain(resource.getLongAmount(), action);
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

    public static class InputOnly extends LongFluidTank {
        protected final ILongFluidTank base;
        public InputOnly(LongFluidTank base) {
            super(base.capacity, base.validator);
            this.base = base;
        }

        @Override
        public long getFluidLongAmount() {
            return base.getFluidLongAmount();
        }

        @Override
        public @NotNull LongFluidStack getFluid() {
            return base.getFluid();
        }

        @Override
        public LongFluidStack drain(long maxDrain, FluidAction action) {
            return LongFluidStack.EMPTY;
        }

        @Override
        public long fillLong(LongFluidStack resource, FluidAction action) {
            return base.fillLong(resource, action);
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return base.isFluidValid(stack);
        }
    }

    public static class OutputOnly extends LongFluidTank {
        protected final ILongFluidTank base;
        public OutputOnly(LongFluidTank base) {
            super(base.capacity, base.validator);
            this.base = base;
        }

        @Override
        public long getFluidLongAmount() {
            return base.getFluidLongAmount();
        }

        @Override
        public @NotNull LongFluidStack getFluid() {
            return base.getFluid();
        }

        @Override
        public LongFluidStack drain(long maxDrain, FluidAction action) {
            return base.drain(maxDrain, action);
        }

        @Override
        public long fillLong(LongFluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return false;
        }
    }
}
