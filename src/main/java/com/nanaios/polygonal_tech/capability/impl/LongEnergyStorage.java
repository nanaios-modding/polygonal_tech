package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;

public class LongEnergyStorage implements ILongEnergyStorage {
    private long energy;
    private final LongSupplier capacity;
    private final BooleanSupplier canReceive;
    private final BooleanSupplier canExtract;

    public LongEnergyStorage(
            LongSupplier capacity,
            BooleanSupplier canReceive,
            BooleanSupplier canExtract
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
    public boolean canExtract() {
        return canExtract.getAsBoolean();
    }

    @Override
    public boolean canReceive() {
        return canReceive.getAsBoolean();
    }

    @Override
    public boolean canOutput() {
        return canExtract.getAsBoolean();
    }

    @Override
    public boolean canInput() {
        return canReceive.getAsBoolean();
    }
}
