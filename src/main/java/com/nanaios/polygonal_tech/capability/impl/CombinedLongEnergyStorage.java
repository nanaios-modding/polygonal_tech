package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.base.CombinedCapability;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

public class CombinedLongEnergyStorage extends CombinedCapability<ILongEnergyStorage> implements ILongEnergyStorage{
    @Override
    public long getLongEnergyStored() {
        return 0;
    }

    @Override
    public long getLongMaxEnergyStored() {
        return 0;
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
