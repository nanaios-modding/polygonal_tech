package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.base.CombinedCapability;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.util.MathUtil;

public class CombinedLongEnergyStorage extends CombinedCapability<ILongEnergyStorage> implements ILongEnergyStorage{
    @Override
    public long getLongEnergyStored() {
        long totalEnergy = 0;
        for(ILongEnergyStorage storage : capabilities) {
            long energy = storage.getLongEnergyStored();
            totalEnergy = MathUtil.addExact(totalEnergy, energy);
        }
        return totalEnergy;
    }

    @Override
    public long getLongMaxEnergyStored() {
        long totalMaxEnergy = 0;
        for(ILongEnergyStorage storage : capabilities) {
            long maxEnergy = storage.getLongMaxEnergyStored();
            totalMaxEnergy = MathUtil.addExact(totalMaxEnergy, maxEnergy);
        }
        return totalMaxEnergy;
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        for(ILongEnergyStorage storage : capabilities) {
            long received = storage.receiveLongEnergy(maxReceive, simulate);
            maxReceive -= received;
            if(maxReceive <= 0) {
                break;
            }
        }
        return maxReceive;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        for(ILongEnergyStorage storage : capabilities) {
            long extracted = storage.extractLongEnergy(maxExtract, simulate);
            maxExtract -= extracted;
            if(maxExtract <= 0) {
                break;
            }
        }
        return maxExtract;
    }

    @Override
    public boolean canExtract() {
        for(ILongEnergyStorage storage : capabilities) {
            if(storage.canExtract()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canReceive() {
        for(ILongEnergyStorage storage : capabilities) {
            if(storage.canReceive()) {
                return true;
            }
        }
        return false;
    }
}
