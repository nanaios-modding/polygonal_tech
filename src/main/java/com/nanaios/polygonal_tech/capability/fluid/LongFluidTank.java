package com.nanaios.polygonal_tech.capability.fluid;

import com.nanaios.polygonal_tech.capability.base.BaseCapability;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.jetbrains.annotations.NotNull;

import java.util.function.LongSupplier;
import java.util.function.Predicate;

public class LongFluidTank extends BaseCapability implements ILongFluidTank {
    protected final LongSupplier capacity;
    protected final Predicate<FluidStack> validator;
    protected LongFluidStack fluid = LongFluidStack.EMPTY;

    public LongFluidTank(boolean arrowInput, boolean arrowOutput, LongSupplier capacity, Predicate<FluidStack> validator) {
        super(arrowInput, arrowOutput);
        this.capacity = capacity;
        this.validator = validator;
    }

    @Override
    public long getFluidLongAmount() {
        return fluid.getLongAmount();
    }

    @Override
    public long getLongCapacity() {
        return capacity.getAsLong();
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) return 0;

        if (action.simulate()) {
            if (fluid.isEmpty()) return Math.min(capacity.getAsLong(), resource.getLongAmount());
            if (!fluid.isFluidEqual(resource)) return 0;
            return Math.min(capacity.getAsLong() - fluid.getLongAmount(), resource.getLongAmount());
        }

        if (fluid.isEmpty()) {
            fluid = new LongFluidStack(resource, Math.min(capacity.getAsLong(), resource.getLongAmount()));
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
            return fluid.getLongAmount();
        }

        if (!fluid.isFluidEqual(resource)) return 0;

        long filled = capacity.getAsLong() - fluid.getLongAmount();

        if (resource.getLongAmount() < filled) {
            fluid.grow(resource.getLongAmount());
            filled = resource.getLongAmount();
        } else {
            fluid.setAmount(capacity.getAsLong());
        }

        if (filled > 0) triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
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
    @NotNull
    public LongFluidStack drain(long maxDrain, FluidAction action) {

        long drained = maxDrain;
        if (fluid.getLongAmount() < drained) {
            drained = fluid.getLongAmount();
        }

        LongFluidStack stack = new LongFluidStack(fluid, drained);
        if (action.execute() && drained > 0) {
            fluid.shrink(drained);
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
        }

        return stack;
    }
}
