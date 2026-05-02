package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken

object PolygonalTechCapabilities {
    val LONG_ENERGY: Capability<ILongEnergyStorage> = CapabilityManager.get(object : CapabilityToken<ILongEnergyStorage>() {})
    val LONG_FLUID_HANDLER: Capability<ILongFluidHandler> = CapabilityManager.get(object : CapabilityToken<ILongFluidHandler>() {})
}