package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken

/**
 * MOD固有の大容量（[Long]型）を扱うCapabilityに関する参照をまとめて保持し、提供することを目的とするオブジェクト。
 * 各タイルやアイテムにおいて[Capability]を要求・公開する際のキーとして機能する。
 */
object PolygonalTechCapabilities {
    /** 
     * 通常の[IEnergyStorage]に代わって、[Long]型のエネルギー量を扱える[ILongEnergyStorage]のCapabilityインスタンス。 
     */
    val LONG_ENERGY: Capability<ILongEnergyStorage> = CapabilityManager.get(object : CapabilityToken<ILongEnergyStorage>() {})
    
    /** 
     * 通常の[IFluidHandler]に代わって、[Long]型の流体量を扱える[ILongFluidHandler]のCapabilityインスタンス。 
     */
    val LONG_FLUID_HANDLER: Capability<ILongFluidHandler> = CapabilityManager.get(object : CapabilityToken<ILongFluidHandler>() {})
}