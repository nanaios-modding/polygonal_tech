package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.InputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.OutputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.IItemSlotFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongEnergyStorageFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongFluidTankFaceBuilder
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.InputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.LongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.OutputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.io.IIOMode
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.InputOnlyItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.ItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.OutputOnlyItemSlotHandler
import net.minecraft.network.chat.Component

open class CapabilityBuilder(
    protected val longEnergyStorageList: MutableList<Pair<Component, ILongEnergyStorage>>,
    protected val longFluidHandlerList: MutableList<Pair<Component, ILongFluidHandler>>,
    protected val itemSlotHandlerList: MutableList<Pair<Component, IItemSlotHandler>>,
    protected val longEnergyStorageFaceMap: MutableMap<IFace, Int>,
    protected val longFluidHandlerFaceMap: MutableMap<IFace, Int>,
    protected val itemSlotHandlerFaceMap: MutableMap<IFace, Int>,
) {
    fun energy(name: Component, mode: IIOMode, defaultFace: IFace, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)

        val wrapped = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputWrapperLongEnergyStorage(combined)
            IOMode.OUTPUT -> OutputWrapperLongEnergyStorage(combined)
            else -> combined
        } else combined

        val pair = Pair(name, wrapped)
        longEnergyStorageList.add(pair)
        longEnergyStorageFaceMap[defaultFace] = longEnergyStorageList.size - 1
    }

    fun fluid(name: Component, mode: IIOMode, defaultFace: IFace, builder: ILongFluidTankFaceBuilder.() -> Unit) {
        val fluidBuilder = ILongFluidTankFaceBuilder()
        fluidBuilder.builder()

        val (capabilities) = fluidBuilder

        val handler = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyLongFluidHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyLongFluidHandler(capabilities)
            else -> LongFluidHandler(capabilities)
        } else LongFluidHandler(capabilities)

        val pair = Pair(name, handler)
        longFluidHandlerList.add(pair)
        longFluidHandlerFaceMap[defaultFace] = longFluidHandlerList.size - 1
    }

    fun item(name: Component, mode: IIOMode, defaultFace: IFace,  builder: IItemSlotFaceBuilder.()-> Unit) {
        val itemBuilder = IItemSlotFaceBuilder()
        itemBuilder.builder()

        val (capabilities) = itemBuilder

        val handler = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyItemSlotHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyItemSlotHandler(capabilities)
            else -> ItemSlotHandler(capabilities)
        } else ItemSlotHandler(capabilities)

        val pair = Pair(name, handler)
        itemSlotHandlerList.add(pair)
        itemSlotHandlerFaceMap[defaultFace] = itemSlotHandlerList.size - 1
    }
}

