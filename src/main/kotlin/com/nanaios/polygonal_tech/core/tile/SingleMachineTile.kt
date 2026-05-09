package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.builder.CapabilityBuilder
import com.nanaios.polygonal_tech.core.capability.CapabilityManager
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
) : SingleTile(id, pos, state) {
    companion object {
        const val NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP = "long_energy_storage_face_map"
        const val NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP = "long_fluid_handler_face_map"
        const val NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP = "item_slot_handler_face_map"
    }

    protected val _longEnergyStorageMap = mutableMapOf<IFace,CapabilityManager<ILongEnergyStorage>>()
    protected val _longFluidHandlerMap = mutableMapOf<IFace,CapabilityManager<ILongFluidHandler>>()
    protected val _itemSlotHandlerMap = mutableMapOf<IFace,CapabilityManager<IItemSlotHandler>>()

    override fun onLoad() {
        super.onLoad()
        if (isClientSide) return
    }

    protected fun capability(builder: CapabilityBuilder.() -> Unit) {
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        val face = DirectionFace.from(defaultFront, currentFront, side)
        val capability = getCapability(cap, face)
        return if (capability.isPresent) capability else super.getCapability(cap, side)
    }

    override fun <T> getCapability(cap: Capability<T>, face: IFace): LazyOptional<T> = LazyOptional.empty()
}