package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.base.CombinedContainer;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.core.Direction;

import java.util.List;

public class CombinedLongEnergyStorage extends CombinedContainer<ILongEnergyStorage> implements ILongEnergyStorage {
    public CombinedLongEnergyStorage(Direction side, List<BaseContainer<ILongEnergyStorage>> containers) {
        super(side, containers);
    }

    @Override
    public long getLongEnergyStored() {
        long totalEnergy = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            if (container.isActive()) continue;
            ILongEnergyStorage storage = container.getInput(side);
            long energy = storage.getLongEnergyStored();
            totalEnergy = MathUtil.addExact(totalEnergy, energy);
        }
        return totalEnergy;
    }

    @Override
    public long getLongMaxEnergyStored() {
        long totalMaxEnergy = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            ILongEnergyStorage storage = container.getInput(side);
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

            long received = storage.receiveLongEnergy(maxReceive - totalReceived, simulate);
            totalReceived = MathUtil.addExact(totalReceived, received);
            if (totalReceived >= maxReceive) {
                break;
            }
        }
        return totalReceived;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        long totalExtracted = 0;
        for (BaseContainer<ILongEnergyStorage> container : containers) {
            ILongEnergyStorage storage = container.getInput(side);

            long extracted = storage.extractLongEnergy(maxExtract - totalExtracted, simulate);
            totalExtracted = MathUtil.addExact(totalExtracted, extracted);
            if (totalExtracted >= maxExtract) {
                break;
            }
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
