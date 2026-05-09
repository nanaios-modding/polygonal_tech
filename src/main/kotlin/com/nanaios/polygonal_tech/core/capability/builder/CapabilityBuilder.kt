package com.nanaios.polygonal_tech.core.capability.builder

import com.nanaios.polygonal_tech.core.capability.ICapabilityInfo
import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.IItemSlotFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongEnergyStorageFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongFluidTankFaceBuilder

open class CapabilityBuilder(
) {

    fun energy(info: ICapabilityInfo, defaultFace: IFace, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)
    }

    fun fluid(info: ICapabilityInfo, defaultFace: IFace, builder: ILongFluidTankFaceBuilder.() -> Unit) {
        val fluidBuilder = ILongFluidTankFaceBuilder()
        fluidBuilder.builder()

        val (capabilities) = fluidBuilder
    }

    fun item(info: ICapabilityInfo, defaultFace: IFace, builder: IItemSlotFaceBuilder.()-> Unit) {
        val itemBuilder = IItemSlotFaceBuilder()
        itemBuilder.builder()

        val (capabilities) = itemBuilder

    }
}