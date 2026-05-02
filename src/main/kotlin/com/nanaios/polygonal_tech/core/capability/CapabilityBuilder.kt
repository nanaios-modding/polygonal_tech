package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.InputOnlyLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.OutputOnlyLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.ILongEnergyStorageFaceBuilder
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import net.minecraft.network.chat.MutableComponent

open class CapabilityBuilder(
    protected val longEnergyStorageList: MutableList<Pair<MutableComponent, ILongEnergyStorage>>,
    protected val longFluidHandlerList: MutableList<Pair<MutableComponent, ILongFluidHandler>>,
    protected val itemSlotHandlerList: MutableList<Pair<MutableComponent, IItemSlotHandler>>,
    protected val longEnergyStorageFaceMap: MutableMap<IFace, Int>,
    protected val longFluidHandlerFaceMap: MutableMap<IFace, Int>,
    protected val itemSlotHandlerFaceMap: MutableMap<IFace, Int>,
) {
    fun energy(name: MutableComponent, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities, mode, face) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)

        val wrapped = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyLongEnergyStorage(combined)
            IOMode.OUTPUT -> OutputOnlyLongEnergyStorage(combined)
            else -> combined
        } else combined

        val pair = Pair(name, wrapped)
        longEnergyStorageList.add(pair)
        longEnergyStorageFaceMap[face] = longEnergyStorageList.size - 1
    }
}

