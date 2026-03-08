package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.base.CombinedContainer;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombinedLongFluidContainer extends CombinedContainer<ILongFluidTank> implements ILongFluidTank {
    public CombinedLongFluidContainer(Direction side, List<BaseContainer<ILongFluidTank>> baseContainers) {
        super(side, baseContainers);
    }

    @Override
    public int getTanks() {
        return containers.size();
    }

    @Override
    public long getFluidLongAmount() {
        long totalAmount = 0;
        for (BaseContainer<ILongFluidTank> container : containers) {
            if (!container.isActive()) continue;
            ILongFluidTank tank = container.getInput(side);
            long amount = tank.getFluidLongAmount();
            totalAmount = MathUtil.addExact(totalAmount, amount);
        }
        return totalAmount;
    }

    @Override
    public long getLongCapacity() {
        long totalCapacity = 0;
        for (BaseContainer<ILongFluidTank> container : containers) {
            if(!container.isActive()) continue;
            ILongFluidTank tank = container.getInput(side);
            long capacity = tank.getLongCapacity();
            totalCapacity = MathUtil.addExact(totalCapacity, capacity);
        }
        return totalCapacity;
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        long totalFilled = 0;
        for (BaseContainer<ILongFluidTank> container : containers) {
            if (!container.isActive()) continue;
            ILongFluidTank tank = container.getInput(side);

            long filled = tank.fillLong(resource, action);
            totalFilled = MathUtil.addExact(totalFilled, filled);
            if (totalFilled >= resource.getLongAmount()) {
                break;
            }
        }
        return totalFilled;
    }

    @Override
    public @NotNull LongFluidStack getFluid() {
        return LongFluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        for (BaseContainer<ILongFluidTank> container : containers) {
            if (!container.isActive()) continue;
            ILongFluidTank tank = container.getInput(side);
            if (tank.isFluidValid(stack)) return true;
        }
        return false;
    }

    /// どのスタックかわからないため、空のスタックを返す
    @Override
    public LongFluidStack drain(long maxDrain, FluidAction action) {
        return LongFluidStack.EMPTY;
    }

    @Override
    public @NotNull LongFluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= containers.size()) return LongFluidStack.EMPTY;
        BaseContainer<ILongFluidTank> container = containers.get(tank);
        if (!container.isActive()) return LongFluidStack.EMPTY;
        ILongFluidTank tankCapability = container.getInput(side);
        return tankCapability.getFluidInTank(0);
    }

    @Override
    public @NotNull LongFluidStack drain(LongFluidStack resource, FluidAction action) {
        long totalDrained = 0;
        for (BaseContainer<ILongFluidTank> container : containers) {
            if (!container.isActive()) continue;
            ILongFluidTank tank = container.getInput(side);

            if(!tank.isFluidValid(resource)) continue;

            LongFluidStack drained = tank.drain(resource, action);
            totalDrained = MathUtil.addExact(totalDrained, drained.getLongAmount());
            if (totalDrained >= resource.getLongAmount()) {
                break;
            }
        }
        return LongFluidStack.of(getFluid());
    }

    /// どのスタックかわからないため、空のスタックを返す
    @Override
    public @NotNull LongFluidStack drain(int maxDrain, FluidAction action) {
        return LongFluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if (tank < 0 || tank >= containers.size()) return false;
        BaseContainer<ILongFluidTank> container = containers.get(tank);
        if (!container.isActive()) return false;
        ILongFluidTank tankCapability = container.getInput(side);
        return tankCapability.isFluidValid(stack);
    }
}
