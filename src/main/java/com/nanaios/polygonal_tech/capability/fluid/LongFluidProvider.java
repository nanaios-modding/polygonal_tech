package com.nanaios.polygonal_tech.capability.fluid;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;

import java.util.function.Consumer;

public class LongFluidProvider extends BaseProvider<ILongFluidTank,LongFluidCombinedCapability> {
    public static String NBT_LONG_FLUID = "long_fluid";
    public LongFluidProvider(Consumer<CapabilityUpdateEvent> capabilityUpdateListener) {
        super(LongFluidCombinedCapability::new, capabilityUpdateListener);
    }
}
