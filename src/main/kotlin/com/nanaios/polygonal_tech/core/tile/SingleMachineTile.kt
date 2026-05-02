package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.CapabilityBuilder
import com.nanaios.polygonal_tech.core.capability.PolygonalTechCapabilities
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.network.sync.ISyncValue
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.util.LazyOptional
import kotlin.collections.mutableMapOf

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
) : SingleTile(id, pos, state) {
    protected val longEnergyStorageList = mutableListOf<Pair<MutableComponent, ILongEnergyStorage>>()
    protected val longFluidHandlerList = mutableListOf<Pair<MutableComponent, ILongFluidHandler>>()
    protected val itemSlotHandlerList = mutableListOf<Pair<MutableComponent, IItemSlotHandler>>()

    protected val longEnergyLazyList = mutableListOf<LazyOptional<ILongEnergyStorage>>()
    protected val longFluidHandlerLazyList = mutableListOf<LazyOptional<ILongFluidHandler>>()
    protected val itemSlotHandlerLazyList = mutableListOf<LazyOptional<IItemSlotHandler>>()

    protected val longEnergyStorageFaceMap = mutableMapOf<IFace, Int>()
    protected val longFluidHandlerFaceMap = mutableMapOf<IFace, Int>()
    protected val itemSlotHandlerFaceMap = mutableMapOf<IFace, Int>()

    protected fun capability(builder: CapabilityBuilder.() -> Unit) {
        CapabilityBuilder(
            longEnergyStorageList,
            longFluidHandlerList,
            itemSlotHandlerList,
            longEnergyStorageFaceMap,
            longFluidHandlerFaceMap,
            itemSlotHandlerFaceMap
        ).builder()
    }

    override fun <T> getCapability(cap: Capability<T>, face: IFace): LazyOptional<T> = when (cap) {
        ForgeCapabilities.ENERGY, PolygonalTechCapabilities.LONG_ENERGY -> {
            val index = longEnergyStorageFaceMap[face] ?: return LazyOptional.empty()
            longEnergyLazyList[index].cast()
        }

        ForgeCapabilities.FLUID_HANDLER, PolygonalTechCapabilities.LONG_FLUID_HANDLER -> {
            val index = longFluidHandlerFaceMap[face] ?: return LazyOptional.empty()
            longFluidHandlerLazyList[index].cast()
        }

        ForgeCapabilities.ITEM_HANDLER -> {
            val index = itemSlotHandlerFaceMap[face] ?: return LazyOptional.empty()
            itemSlotHandlerLazyList[index].cast()
        }

        else -> LazyOptional.empty()
    }

    override fun save(tag: CompoundTag) {
        super.save(tag)

    }



    protected infix fun <V : ISyncValue> V.on(type: ISyncType): V {
        this.type = type
        this.storage = this@SingleMachineTile
        return this
    }
}