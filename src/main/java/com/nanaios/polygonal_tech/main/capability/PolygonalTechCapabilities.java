package com.nanaios.polygonal_tech.main.capability;

import com.nanaios.polygonal_tech.main.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.main.capability.interfaces.ILongFluidTank;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class PolygonalTechCapabilities {
    public static final Capability<ILongEnergyStorage> LONG_ENERGY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<ILongFluidTank> LONG_FLUID_HANDLER = CapabilityManager.get(new CapabilityToken<>(){});
}
