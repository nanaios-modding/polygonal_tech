package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.IDirectionFunction;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.core.Direction;

import java.util.function.LongSupplier;

public class LongEnergyStorage implements ILongEnergyStorage {
    private long energy;
    private final LongSupplier capacity;
    private final IDirectionFunction canReceive;
    private final IDirectionFunction canExtract;

    public LongEnergyStorage(
            LongSupplier capacity,
            IDirectionFunction canReceive,
            IDirectionFunction canExtract
    ) {
        this.capacity = capacity;
        this.canReceive = canReceive;
        this.canExtract = canExtract;
    }

    @Override
    public long getLongEnergyStored() {
        return energy;
    }

    @Override
    public long getLongMaxEnergyStored() {
        return capacity.getAsLong();
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        long energyReceived = Math.min(capacity.getAsLong() - energy, maxReceive);
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        long energyExtracted = Math.min(energy, maxExtract);
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public boolean canReceive(Direction side) {
        return canReceive.get(side);
    }

    @Override
    public boolean canExtract(Direction side) {
        return canExtract.get(side);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return MathUtil.longToInt(receiveLongEnergy(maxReceive, simulate));
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return MathUtil.longToInt(extractLongEnergy(maxExtract, simulate));
    }

    @Override
    public int getEnergyStored() {
        return MathUtil.longToInt(energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return MathUtil.longToInt(capacity.getAsLong());
    }

    @Override
    public boolean canExtract() {
        return canExtract.get(null);
    }

    @Override
    public boolean canReceive() {
        return canReceive.get(null);
    }
}
