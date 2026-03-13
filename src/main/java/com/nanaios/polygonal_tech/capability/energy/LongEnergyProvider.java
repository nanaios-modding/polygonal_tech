package com.nanaios.polygonal_tech.capability.energy;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;

import java.util.function.Consumer;

public class LongEnergyProvider extends BaseProvider<ILongEnergyStorage,LongEnergyCombinedCapability> {
    public static final String NBT_LONG_ENERGY = "long_energy";
    public LongEnergyProvider(Consumer<CapabilityUpdateEvent> capabilityUpdateListener) {
        super(LongEnergyCombinedCapability::new, capabilityUpdateListener);
    }
}
