package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.base.CombinedContainer;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.core.Direction;

import java.util.List;

public class CombinedLongEnergyContainer extends CombinedContainer<ILongEnergyStorage> implements ILongEnergyStorage {
    public CombinedLongEnergyContainer(Direction side, List<BaseContainer<ILongEnergyStorage>> containers) {
        super(side, containers);
    }

    @Override
    public long getLongEnergyStored() {
        long totalEnergy = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            if (!container.isActive()) continue;
            ILongEnergyStorage storage = container.getBase();
            long energy = storage.getLongEnergyStored();
            totalEnergy = MathUtil.addExact(totalEnergy, energy);
        }
        return totalEnergy;
    }

    @Override
    public long getLongMaxEnergyStored() {
        long totalMaxEnergy = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            ILongEnergyStorage storage = container.getBase();
            long maxEnergy = storage.getLongMaxEnergyStored();
            totalMaxEnergy = MathUtil.addExact(totalMaxEnergy, maxEnergy);
        }
        return totalMaxEnergy;
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        long totalReceived = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            ILongEnergyStorage storage = container.getInput(side);
            if(storage == null) continue;
            totalReceived += storage.receiveLongEnergy(maxReceive - totalReceived, simulate);
            if (totalReceived >= maxReceive) break;
        }
        return totalReceived;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        long totalExtracted = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            ILongEnergyStorage storage = container.getOutput(side);
            if(storage == null) continue;

            totalExtracted += storage.extractLongEnergy(maxExtract - totalExtracted, simulate);
            if (totalExtracted >= maxExtract) break;
        }
        return totalExtracted;
    }

    @Override
    public boolean canExtract() {
        for(Direction side : Direction.values()) {
            if(canOutput(side)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canReceive() {
        for(Direction side : Direction.values()) {
            if(canInput(side)) {
                return true;
            }
        }
        return false;
    }
}
