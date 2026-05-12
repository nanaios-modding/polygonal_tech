package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.builder.CapabilityBuilder
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.IOFace
import com.nanaios.polygonal_tech.core.capability.holder.ICapabilityHolder
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

        val DEFAULT_MAPPING_FACE_TO_FACE: Map<DirectionFace, IFace> = mapOf(
            DirectionFace.DOWN to DirectionFace.DOWN,
            DirectionFace.UP to DirectionFace.UP,
            DirectionFace.FRONT to DirectionFace.FRONT,
            DirectionFace.BACK to DirectionFace.BACK,
            DirectionFace.LEFT to IOFace.INPUT_1,
            DirectionFace.RIGHT to IOFace.OUTPUT_1
        )
    }

    protected lateinit var _holder: ICapabilityHolder
    protected open val _mappingFaceToFace: MutableMap<DirectionFace, IFace> = mutableMapOf()

    protected open fun capability(builder: CapabilityBuilder.() -> Unit) {
        val instance = CapabilityBuilder()
        instance.builder()
        _holder = instance.createHolder()
    }



    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        val face = DirectionFace.from(defaultFront, currentFront, side)
        val capability = getCapability(cap, face)
        return if (capability.isPresent) capability else super.getCapability(cap, side)
    }

    override fun <T> getCapability(cap: Capability<T>, face: IFace): LazyOptional<T> {
        val mappedFace = _mappingFaceToFace[face] ?: return LazyOptional.empty()

        return LazyOptional.empty()
    }


}