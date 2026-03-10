package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.impl.LongFluidTank;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.function.LongSupplier;
import java.util.function.Predicate;

public class LongFluidContainer extends BaseContainer<ILongFluidTank> implements ILongFluidTank{
    public static String STORED_FLUID_KEY = "stored";

    public LongFluidContainer(ILongFluidTank base) {
        super(base);
    }

    public static LongFluidContainer create(LongSupplier capacity, Predicate<FluidStack> validator) {
        return new LongFluidContainer(new LongFluidTank(capacity, validator));
    }

    @Override
    public long getFluidLongAmount() {
        return base.getFluidLongAmount();
    }

    @Override
    public long getLongCapacity() {
        return base.getLongCapacity();
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        return base.fillLong(resource, action);
    }

    @Override
    public @NotNull LongFluidStack getFluid() {
        return base.getFluid();
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return base.isFluidValid(stack);
    }

    @Override
    public LongFluidStack drain(long maxDrain, FluidAction action) {
        return base.drain(maxDrain, action);
    }

    @Override
    public @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        return base.drain(resource, action);
    }

    @Override
    public @NotNull LongFluidStack drain(int maxDrain, FluidAction action) {
        return base.drain(maxDrain, action);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(STORED_FLUID_KEY, base.getFluid().writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(STORED_FLUID_KEY)) {
            CompoundTag fluidNbt = nbt.getCompound(STORED_FLUID_KEY);
            LongFluidStack stack = LongFluidStack.loadLongFluidStackFromNBT(fluidNbt);
            base.fillLong(stack, FluidAction.EXECUTE);
        }
    }
}
