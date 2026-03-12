package com.nanaios.polygonal_tech.capability.fluid;

import com.nanaios.polygonal_tech.capability.base.BaseCombinedCapability;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidHandler;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LongFluidCombinedCapability extends BaseCombinedCapability<ILongFluidTank> implements ILongFluidHandler {
    public LongFluidCombinedCapability(@Nullable Direction side, List<ILongFluidTank> capabilities) {
        super(side, capabilities);
    }

    @Override
    public int getTanks() {
        return capabilities.size();
    }

    @Override
    public @NotNull LongFluidStack getFluidInTank(int tank) {
        return  capabilities.get(tank).getFluid();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return capabilities.get(tank).isFluidValid(stack);
    }

    @Override
    public long getTankLongCapacity(int tank) {
        return capabilities.get(tank).getLongCapacity();
    }

    @Override
    public @NotNull LongFluidStack drainLong(LongFluidStack resource, FluidAction action) {
        long totalDrained = 0;
        for (ILongFluidTank tank : capabilities) {
            if(!tank.canInput(side)) continue;
            LongFluidStack drained = tank.drain(resource.getLongAmount() - totalDrained, action);
            totalDrained += drained.getAmount();
            if (totalDrained >= resource.getLongAmount()) break;
        }
        return new LongFluidStack(resource, totalDrained);
    }

    @Override
    public @NotNull LongFluidStack drainLong(long maxDrain, FluidAction action) {
        // どの液体が抽出されるかは不定。最初に抽出できた液体が返される。
        for(ILongFluidTank tank : capabilities) {
            if(!tank.canInput(side)) continue;
            LongFluidStack drained = tank.drain(maxDrain, action);
            if (!drained.isEmpty()) return drained;
        }
        return LongFluidStack.EMPTY;
    }

    @Override
    public long fillLong(LongFluidStack resource, FluidAction action) {
        long totalFilled = 0;
        LongFluidStack wantToFill = resource.copy();
        for (ILongFluidTank tank : capabilities) {
            if(!tank.canOutput(side)) continue;
            long filled = tank.fillLong(wantToFill, action);
            totalFilled += filled;
            wantToFill.shrink(filled);
            if (totalFilled >= resource.getLongAmount()) break;
        }
        return totalFilled;
    }
}
