package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidTank
import com.nanaios.polygonal_tech.core.capability.io.IIOMode
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlot

interface ICapabilityFaceBuilder<C: ICapability> {
    var capabilities: MutableList<C>
    var mode: IIOMode
    var defaultFace:IFace

    operator fun C.unaryPlus() {
        capabilities.add(this)
    }

    operator fun C.unaryMinus() {
        capabilities.remove(this)
    }

    operator fun component1() = capabilities
    operator fun component2() = mode
    operator fun component3() = defaultFace
}

class ILongEnergyStorageFaceBuilder : ICapabilityFaceBuilder<ILongEnergyStorage> {
    override var capabilities: MutableList<ILongEnergyStorage> = arrayListOf()
    override var mode: IIOMode = IOMode.NONE
    override var defaultFace: IFace = EmptyFace
}

class ILongFluidTankFaceBuilder : ICapabilityFaceBuilder<ILongFluidTank> {
    override var capabilities: MutableList<ILongFluidTank> = arrayListOf()
    override var mode: IIOMode = IOMode.NONE
    override var defaultFace: IFace = EmptyFace
}

class IItemSlotFaceBuilder:ICapabilityFaceBuilder<IItemSlot> {
    override var capabilities: MutableList<IItemSlot> = arrayListOf()
    override var mode: IIOMode = IOMode.NONE
    override var defaultFace: IFace = EmptyFace
}