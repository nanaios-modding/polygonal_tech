package com.nanaios.polygonal_tech.container;

import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidHandler;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.base.CombinedContainer;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombinedLongFluidContainer extends CombinedContainer<ILongFluidTank> implements ILongFluidHandler {
    public CombinedLongFluidContainer(Direction side, List<BaseContainer<ILongFluidTank>> baseContainers) {
        super(side, baseContainers);
    }

    @Override
    public int getTanks() {
        return containers.size();
    }

    @Override
    public @NotNull LongFluidStack getFluidInTank(int tank) {
        BaseContainer<ILongFluidTank> container = containers.get(tank);
        if(!container.isActive()) return LongFluidStack.EMPTY;
        return container.getBase().getFluid();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        BaseContainer<ILongFluidTank> container = containers.get(tank);
        if(!container.isActive()) return false;
        return container.getBase().isFluidValid(stack);
    }

    @Override
    public long getTankLongCapacity(int tank) {
        long totalCapacity = 0;
        for(BaseContainer<ILongFluidTank> container : containers) {
            if(!container.isActive()) continue;
            ILongFluidTank fluidTank = container.getBase();
            totalCapacity += fluidTank.getLongCapacity();
        }
        return totalCapacity;
    }

    @Override
    public @NotNull LongFluidStack drainLong(LongFluidStack resource, FluidAction action) {
        long wantDrain = resource.getLongAmount();
        for (BaseContainer<ILongFluidTank> container : containers) {
            if(!container.isActive()) continue;
            ILongFluidTank fluidTank = container.getInput(side);
            if(fluidTank == null) continue;

            if (fluidTank.isFluidValid(resource)) {
                LongFluidStack drained = fluidTank.drain(wantDrain, action);
                wantDrain -= drained.getLongAmount();
                if (wantDrain <= 0) break;
            }
        }
        return new LongFluidStack(resource, resource.getLongAmount() - wantDrain);
    }

    @Override
    public @NotNull LongFluidStack drainLong(long maxDrain, FluidAction action) {
        for(BaseContainer<ILongFluidTank> container : containers) {
            if(!container.isActive()) continue;
            ILongFluidTank fluidTank = container.getInput(side);
            if(fluidTank == null) continue;
            LongFluidStack drained = fluidTank.drain(maxDrain, action);
            if (!drained.isEmpty()) return drained;
        }
        return LongFluidStack.EMPTY;
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        long wantFill = resource.getLongAmount();
        for (BaseContainer<ILongFluidTank> container : containers) {
            if(!container.isActive()) continue;
            ILongFluidTank fluidTank = container.getOutput(side);
            if(fluidTank == null) continue;
            if (fluidTank.isFluidValid(resource)) {
                long filled = fluidTank.fillLong(resource, action);
                wantFill -= filled;
                if (wantFill <= 0) break;
            }
        }
        return resource.getLongAmount() - wantFill;
    }
}
