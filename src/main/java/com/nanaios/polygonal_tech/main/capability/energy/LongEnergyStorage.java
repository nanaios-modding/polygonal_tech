package com.nanaios.polygonal_tech.main.capability.energy;

import com.nanaios.polygonal_tech.main.capability.base.BaseCapability;
import com.nanaios.polygonal_tech.main.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.main.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.main.event.PolygonalTechEventType;

import java.util.function.LongSupplier;

public class LongEnergyStorage extends BaseCapability implements ILongEnergyStorage {
    private final LongSupplier capacity;
    private long energy;

    public LongEnergyStorage(boolean arrowInput, boolean arrowOutput, LongSupplier capacity) {
        super(arrowInput, arrowOutput);
        this.capacity = capacity;
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
    public void setLongEnergy(long energy) {
        this.energy = energy;
        triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        if (!canReceive()) return 0;

        long energyReceived = Math.min(capacity.getAsLong() - energy, maxReceive);
        if (!simulate) {
            energy += energyReceived;
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
        }
        return energyReceived;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        long energyExtracted = Math.min(energy, maxExtract);
        if (!simulate) {
            energy -= energyExtracted;
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
        }
        return energyExtracted;
    }

    @Override
    public boolean canExtract() {
        return arrowOutput;
    }

    @Override
    public boolean canReceive() {
        return arrowInput;
    }
}
