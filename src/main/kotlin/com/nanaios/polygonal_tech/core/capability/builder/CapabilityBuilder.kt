package com.nanaios.polygonal_tech.core.capability.builder

import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.InputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.OutputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.InputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.LongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.OutputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.holder.CapabilityHolder
import com.nanaios.polygonal_tech.core.capability.holder.ICapabilityHolder
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.InputOnlyItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.ItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.OutputOnlyItemSlotHandler

open class CapabilityBuilder {
    val longEnergyStorageMap = mutableMapOf<IFace, ILongEnergyStorage>()
    val longFluidHandlerMap = mutableMapOf<IFace, ILongFluidHandler>()
    val itemSlotHandlerMap = mutableMapOf<IFace, IItemSlotHandler>()

    fun energy(face: IFace, mode: IOMode, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)

        val wrapper = when(mode) {
            IOMode.INPUT -> InputWrapperLongEnergyStorage(combined)
            IOMode.OUTPUT -> OutputWrapperLongEnergyStorage(combined)
            else -> combined
        }

        longEnergyStorageMap[face] = wrapper
    }

    fun fluid(face: IFace, mode: IOMode,  builder: ILongFluidTankFaceBuilder.() -> Unit) {
        val fluidBuilder = ILongFluidTankFaceBuilder()
        fluidBuilder.builder()

        val (capabilities) = fluidBuilder

        val wrapper = when(mode) {
            IOMode.INPUT -> InputOnlyLongFluidHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyLongFluidHandler(capabilities)
            else -> LongFluidHandler(capabilities)
        }

        longFluidHandlerMap[face] = wrapper
    }

    fun item(face: IFace, mode: IOMode, builder: IItemSlotFaceBuilder.()-> Unit) {
        val itemBuilder = IItemSlotFaceBuilder()
        itemBuilder.builder()

        val (capabilities) = itemBuilder

        val wrapper = when(mode) {
            IOMode.INPUT -> InputOnlyItemSlotHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyItemSlotHandler(capabilities)
            else -> ItemSlotHandler(capabilities)
        }

        itemSlotHandlerMap[face] = wrapper
    }

    fun createHolder(): ICapabilityHolder {
        return CapabilityHolder(longEnergyStorageMap,longFluidHandlerMap,itemSlotHandlerMap)
    }
}