package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidTank
import com.nanaios.polygonal_tech.core.capability.io.IIOMode
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlot

interface ICapabilityFaceBuilder<C: ICapability> {
    var capabilities: MutableList<C>

    operator fun C.unaryPlus() {
        capabilities.add(this)
    }

    operator fun C.unaryMinus() {
        capabilities.remove(this)
    }

    operator fun component1() = capabilities
}

class ILongEnergyStorageFaceBuilder : ICapabilityFaceBuilder<ILongEnergyStorage> {
    override var capabilities: MutableList<ILongEnergyStorage> = arrayListOf()
}

class ILongFluidTankFaceBuilder : ICapabilityFaceBuilder<ILongFluidTank> {
    override var capabilities: MutableList<ILongFluidTank> = arrayListOf()
}

class IItemSlotFaceBuilder:ICapabilityFaceBuilder<IItemSlot> {
    override var capabilities: MutableList<IItemSlot> = arrayListOf()
}