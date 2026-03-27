package com.nanaios.polygonal_tech.main.capability.energy;

import com.nanaios.polygonal_tech.main.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.main.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.main.event.CapabilityUpdateEvent;

import java.util.function.Consumer;

public class LongEnergyProvider extends BaseProvider<ILongEnergyStorage,LongEnergyCombinedCapability> {
    public static final String NBT_LONG_ENERGY = "long_energy";
    public LongEnergyProvider(Consumer<CapabilityUpdateEvent> capabilityUpdateListener) {
        super(LongEnergyCombinedCapability::new, capabilityUpdateListener);
    }
}
