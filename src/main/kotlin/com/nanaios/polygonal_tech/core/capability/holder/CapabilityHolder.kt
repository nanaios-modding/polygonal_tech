package com.nanaios.polygonal_tech.core.capability.holder

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.register.PolygonalTechRegistries
import net.minecraft.nbt.CompoundTag
import java.util.Collections

open class CapabilityHolder(
    protected val _longEnergyStorageMap:MutableMap<IFace, ILongEnergyStorage>,
    protected val _longFluidHandlerMap:MutableMap<IFace, ILongFluidHandler>,
    protected val _itemSlotHandlerMap:MutableMap<IFace, IItemSlotHandler>
):ICapabilityHolder {
    override val longEnergyStorageMap: Map<IFace, ILongEnergyStorage>
        get() = Collections.unmodifiableMap(_longEnergyStorageMap)
    override val longFluidHandlerMap: Map<IFace, ILongFluidHandler>
        get() = Collections.unmodifiableMap(_longFluidHandlerMap)
    override val itemSlotHandlerMap: Map<IFace, IItemSlotHandler>
        get() = Collections.unmodifiableMap(_itemSlotHandlerMap)
}