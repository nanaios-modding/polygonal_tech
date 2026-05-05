package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.CapabilityBuilder
import com.nanaios.polygonal_tech.core.capability.PolygonalTechCapabilities
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.util.LazyOptional
import java.util.Collections

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
) : SingleTile(id, pos, state) {
    companion object {
        const val NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP = "long_energy_storage_face_map"
        const val NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP = "long_fluid_handler_face_map"
        const val NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP = "item_slot_handler_face_map"
    }

    protected val _longEnergyStorageList = mutableListOf<Pair<Component, ILongEnergyStorage>>()
    protected val _longFluidHandlerList = mutableListOf<Pair<Component, ILongFluidHandler>>()
    protected val _itemSlotHandlerList = mutableListOf<Pair<Component, IItemSlotHandler>>()

    val longEnergyStorageList: List<Pair<Component, ILongEnergyStorage>> = Collections.unmodifiableList(_longEnergyStorageList)
    val longFluidHandlerList: List<Pair<Component, ILongFluidHandler>> = Collections.unmodifiableList(_longFluidHandlerList)
    val itemSlotHandlerList: List<Pair<Component, IItemSlotHandler>> = Collections.unmodifiableList(_itemSlotHandlerList)

    protected val _longEnergyStorageLazyList = mutableListOf<LazyOptional<ILongEnergyStorage>>()
    protected val _longFluidHandlerLazyList = mutableListOf<LazyOptional<ILongFluidHandler>>()
    protected val _itemSlotHandlerLazyList = mutableListOf<LazyOptional<IItemSlotHandler>>()

    protected val _longEnergyStorageFaceMap = mutableMapOf<IFace, Int>()
    protected val _longFluidHandlerFaceMap = mutableMapOf<IFace, Int>()
    protected val _itemSlotHandlerFaceMap = mutableMapOf<IFace, Int>()

    protected val longEnergyStorageFaceMap: Map<IFace, Int> = Collections.unmodifiableMap(_longEnergyStorageFaceMap)
    protected val longFluidHandlerFaceMap: Map<IFace, Int> = Collections.unmodifiableMap(_longFluidHandlerFaceMap)
    protected val itemSlotHandlerFaceMap: Map<IFace, Int> = Collections.unmodifiableMap(_itemSlotHandlerFaceMap)

    override fun onLoad() {
        super.onLoad()
        if (isClientSide) return

        PolygonalTech.LOGGER.debug("longEnergyStorageList: {}", _longEnergyStorageList)
        PolygonalTech.LOGGER.debug("longFluidHandlerList: {}", _longFluidHandlerList)
        PolygonalTech.LOGGER.debug("itemSlotHandlerList: {}", _itemSlotHandlerList)

        PolygonalTech.LOGGER.debug("longEnergyStorageFaceMap: {}", _longEnergyStorageFaceMap)
        PolygonalTech.LOGGER.debug("longFluidHandlerFaceMap: {}", _longFluidHandlerFaceMap)
        PolygonalTech.LOGGER.debug("itemSlotHandlerFaceMap: {}", _itemSlotHandlerFaceMap)
    }

    protected fun capability(builder: CapabilityBuilder.() -> Unit) {
        CapabilityBuilder(
            _longEnergyStorageList,
            _longFluidHandlerList,
            _itemSlotHandlerList,
            _longEnergyStorageFaceMap,
            _longFluidHandlerFaceMap,
            _itemSlotHandlerFaceMap
        ).builder()

        initCaps()
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        val face = DirectionFace.from(defaultFront, currentFront, side)
        val capability = getCapability(cap, face)
        return if (capability.isPresent) capability else super.getCapability(cap, side)
    }

    override fun <T> getCapability(cap: Capability<T>, face: IFace): LazyOptional<T> = when (cap) {
        ForgeCapabilities.ENERGY, PolygonalTechCapabilities.LONG_ENERGY -> {
            val index = _longEnergyStorageFaceMap[face] ?: return LazyOptional.empty()
            _longEnergyStorageLazyList[index].cast()
        }

        ForgeCapabilities.FLUID_HANDLER, PolygonalTechCapabilities.LONG_FLUID_HANDLER -> {
            val index = _longFluidHandlerFaceMap[face] ?: return LazyOptional.empty()
            _longFluidHandlerLazyList[index].cast()
        }

        ForgeCapabilities.ITEM_HANDLER -> {
            val index = _itemSlotHandlerFaceMap[face] ?: return LazyOptional.empty()
            _itemSlotHandlerLazyList[index].cast()
        }

        else -> LazyOptional.empty()
    }

    protected fun initCaps() {
        _longEnergyStorageLazyList.clear()
        _longFluidHandlerLazyList.clear()
        _itemSlotHandlerLazyList.clear()

        _longEnergyStorageList.forEach {
            _longEnergyStorageLazyList.add(LazyOptional.of { it.second })
        }
        _longFluidHandlerList.forEach {
            _longFluidHandlerLazyList.add(LazyOptional.of { it.second })
        }
        _itemSlotHandlerList.forEach {
            _itemSlotHandlerLazyList.add(LazyOptional.of { it.second })
        }
    }

    override fun invalidateCaps() {
        _longEnergyStorageLazyList.forEach {
            it.invalidate()
        }
        _longFluidHandlerLazyList.forEach {
            it.invalidate()
        }
        _itemSlotHandlerLazyList.forEach {
            it.invalidate()
        }
    }

    override fun reviveCaps() {
        super.reviveCaps()
        initCaps()
    }

    override fun save(tag: CompoundTag) {
        super.save(tag)

        tag.put(NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP, saveMap(_longEnergyStorageFaceMap))
        tag.put(NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP, saveMap(_longFluidHandlerFaceMap))
        tag.put(NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP, saveMap(_itemSlotHandlerFaceMap))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        loadMap(tag.getCompound(NBT_KEY_LONG_ENERGY_STORAGE_FACE_MAP), _longEnergyStorageFaceMap)
        loadMap(tag.getCompound(NBT_KEY_LONG_FLUID_HANDLER_FACE_MAP), _longFluidHandlerFaceMap)
        loadMap(tag.getCompound(NBT_KEY_ITEM_SLOT_HANDLER_FACE_MAP), _itemSlotHandlerFaceMap)
    }

    protected fun saveMap(map: MutableMap<IFace, Int>): CompoundTag {
        val tag = CompoundTag()
        tag.putInt("size", map.size)
        map.forEach { (face, index) ->
            tag.putInt(face.id.toString(), index)
        }

        return tag
    }

    protected fun loadMap(tag: CompoundTag, map: MutableMap<IFace, Int>) {
        val size = tag.getInt("size")
        if (size <= 0) return

        map.forEach { (face, _) ->
            if (tag.contains(face.id.toString())) {
                map[face] = tag.getInt(face.id.toString())
            } else {
                map.remove(face)
            }
        }
    }
}