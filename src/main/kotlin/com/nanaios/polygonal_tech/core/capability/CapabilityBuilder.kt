package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.InputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.OutputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.ILongEnergyStorageFaceBuilder
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import net.minecraft.network.chat.Component

open class CapabilityBuilder(
    protected val longEnergyStorageList: MutableList<Pair<Component, ILongEnergyStorage>>,
    protected val longFluidHandlerList: MutableList<Pair<Component, ILongFluidHandler>>,
    protected val itemSlotHandlerList: MutableList<Pair<Component, IItemSlotHandler>>,
    protected val longEnergyStorageFaceMap: MutableMap<IFace, Int>,
    protected val longFluidHandlerFaceMap: MutableMap<IFace, Int>,
    protected val itemSlotHandlerFaceMap: MutableMap<IFace, Int>,
) {
    fun energy(name: Component, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities, mode, face) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)

        val wrapped = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputWrapperLongEnergyStorage(combined)
            IOMode.OUTPUT -> OutputWrapperLongEnergyStorage(combined)
            else -> combined
        } else combined

        val pair = Pair(name, wrapped)
        longEnergyStorageList.add(pair)
        longEnergyStorageFaceMap[face] = longEnergyStorageList.size - 1
    }
}

