package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.PolygonalTechCapabilities
import com.nanaios.polygonal_tech.core.capability.builder.CapabilityBuilder
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.IOFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.register.PolygonalTechRegistries
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.util.LazyOptional

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
) : SingleTile(id, pos, state) {
    companion object {
        const val NBT_KEY_FACE_MAP = "face_map"
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

    protected val longEnergyStorageMap = mutableMapOf<IFace, ILongEnergyStorage>()
    protected val longFluidHandlerMap = mutableMapOf<IFace, ILongFluidHandler>()
    protected val itemSlotHandlerMap = mutableMapOf<IFace, IItemSlotHandler>()

    protected val longEnergyStorageLazy = mutableMapOf<IFace, LazyOptional<ILongEnergyStorage>>()
    protected val longFluidHandlerLazy = mutableMapOf<IFace, LazyOptional<ILongFluidHandler>>()
    protected val itemSlotHandlerLazy = mutableMapOf<IFace, LazyOptional<IItemSlotHandler>>()

    protected open val longEnergyStorageMappingFaceToFace: MutableMap<DirectionFace, IFace> = mutableMapOf()
    protected open val longFluidHandlerMappingFaceToFace: MutableMap<DirectionFace, IFace> = mutableMapOf()
    protected open val itemSlotHandlerMappingFaceToFace: MutableMap<DirectionFace, IFace> = mutableMapOf()

    protected open fun defaultFaceMapping() {
        DEFAULT_MAPPING_FACE_TO_FACE.forEach { (directionFace, face) ->
            longEnergyStorageMappingFaceToFace[directionFace] = face
            longFluidHandlerMappingFaceToFace[directionFace] = face
            itemSlotHandlerMappingFaceToFace[directionFace] = face
        }
    }

    override fun onLoad() {
        super.onLoad()
        initCaps()
    }

    protected fun initCaps() {
        longEnergyStorageMap.forEach { (face, storage) ->
            longEnergyStorageLazy[face] = LazyOptional.of { storage }
        }
        longFluidHandlerMap.forEach { (face, handler) ->
            longFluidHandlerLazy[face] = LazyOptional.of { handler }
        }
        itemSlotHandlerMap.forEach { (face, handler) ->
            itemSlotHandlerLazy[face] = LazyOptional.of { handler }
        }
    }

    override fun invalidateCaps() {
        super.invalidateCaps()

        longEnergyStorageLazy.values.forEach { it.invalidate() }
        longFluidHandlerLazy.values.forEach { it.invalidate() }
        itemSlotHandlerLazy.values.forEach { it.invalidate() }

        longEnergyStorageLazy.clear()
        longFluidHandlerLazy.clear()
        itemSlotHandlerLazy.clear()
    }

    override fun reviveCaps() {
        super.reviveCaps()
        initCaps()
    }

    protected open fun capability(builder: CapabilityBuilder.() -> Unit) {
        val instance = CapabilityBuilder(longEnergyStorageMap,longFluidHandlerMap,itemSlotHandlerMap)
        instance.builder()
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        val face = DirectionFace.from(defaultFront, currentFront, side)
        val capability = getCapability(cap, face)
        return if (capability.isPresent) capability else super.getCapability(cap, side)
    }

    override fun <T> getCapability(cap: Capability<T>, face: IFace): LazyOptional<T> {
        if(face is DirectionFace) {
            when(cap) {
                ForgeCapabilities.ENERGY, PolygonalTechCapabilities.LONG_ENERGY -> {
                    longEnergyStorageMappingFaceToFace[face]?.let { mapped ->
                        longEnergyStorageLazy[mapped]?.let { lazyOptional ->
                            return lazyOptional.cast()
                        }
                    }
                }

                ForgeCapabilities.FLUID_HANDLER, PolygonalTechCapabilities.LONG_FLUID_HANDLER -> {
                    longFluidHandlerMappingFaceToFace[face]?.let { mapped ->
                        longFluidHandlerLazy[mapped]?.let { lazyOptional ->
                            return lazyOptional.cast()
                        }
                    }
                }

                ForgeCapabilities.ITEM_HANDLER -> {
                   itemSlotHandlerMappingFaceToFace[face]?.let { mapped ->
                       itemSlotHandlerLazy[mapped]?.let { lazyOptional ->
                           return lazyOptional.cast()
                       }
                   }
                }
            }
        }
        return LazyOptional.empty()
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        if(!tag.contains(NBT_KEY_FACE_MAP)) {
            defaultFaceMapping()
            return
        }

        val faceMappingTag = tag.getCompound(NBT_KEY_FACE_MAP)
        val energyFaceMappingTag = faceMappingTag.getCompound(NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP)
        val fluidFaceMappingTag = faceMappingTag.getCompound(NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP)
        val itemFaceMappingTag = faceMappingTag.getCompound(NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP)

        energyFaceMappingTag.allKeys.forEach { key ->
            val directionFace = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(key))
            val face = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(energyFaceMappingTag.getString(key)))
            if(directionFace is DirectionFace && face != null) {
                longEnergyStorageMappingFaceToFace[directionFace] = face
            }
        }

        fluidFaceMappingTag.allKeys.forEach { key ->
            val directionFace = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(key))
            val face = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(fluidFaceMappingTag.getString(key)))
            if(directionFace is DirectionFace && face != null) {
                longFluidHandlerMappingFaceToFace[directionFace] = face
            }
        }

        itemFaceMappingTag.allKeys.forEach { key ->
            val directionFace = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(key))
            val face = PolygonalTechRegistries.FACE.getValue(ResourceLocation.parse(itemFaceMappingTag.getString(key)))
            if(directionFace is DirectionFace && face != null) {
                itemSlotHandlerMappingFaceToFace[directionFace] = face
            }
        }
    }

    override fun save(tag: CompoundTag) {
        super.save(tag)

        val faceMappingTag = CompoundTag()
        val energyFaceMappingTag = CompoundTag()
        val fluidFaceMappingTag = CompoundTag()
        val itemFaceMappingTag = CompoundTag()

        longEnergyStorageMappingFaceToFace.forEach { (directionFace, face) ->
            val directionFaceKey = PolygonalTechRegistries.FACE.getKey(directionFace)
            val faceKey = PolygonalTechRegistries.FACE.getKey(face)
            energyFaceMappingTag.putString(directionFaceKey.toString(), faceKey.toString())
        }

        longFluidHandlerMappingFaceToFace.forEach { (directionFace, face) ->
            val directionFaceKey = PolygonalTechRegistries.FACE.getKey(directionFace)
            val faceKey = PolygonalTechRegistries.FACE.getKey(face)
            fluidFaceMappingTag.putString(directionFaceKey.toString(), faceKey.toString())
        }

        itemSlotHandlerMappingFaceToFace.forEach { (directionFace, face) ->
            val directionFaceKey = PolygonalTechRegistries.FACE.getKey(directionFace)
            val faceKey = PolygonalTechRegistries.FACE.getKey(face)
            fluidFaceMappingTag.putString(directionFaceKey.toString(), faceKey.toString())
        }

        faceMappingTag.put(NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP,energyFaceMappingTag)
        faceMappingTag.put(NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP,fluidFaceMappingTag)
        faceMappingTag.put(NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP,itemFaceMappingTag)

        tag.put(NBT_KEY_FACE_MAP, faceMappingTag)
    }
}