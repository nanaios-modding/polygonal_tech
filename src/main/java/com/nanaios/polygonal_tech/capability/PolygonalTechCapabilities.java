package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class PolygonalTechCapabilities {
    public static final Capability<ILongEnergyStorage> LONG_ENERGY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<ILongFluidHandler> LONG_FLUID = CapabilityManager.get(new CapabilityToken<>(){});
}
