package com.nanaios.polygonal_tech.core.capability.holder

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.util.INBTSerializable

interface ICapabilityHolder {
    val longEnergyStorageMap: Map<IFace, ILongEnergyStorage>
    val longFluidHandlerMap : Map<IFace, ILongFluidHandler>
    val itemSlotHandlerMap : Map<IFace, IItemSlotHandler>
}