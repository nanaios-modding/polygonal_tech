package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.util.face.DirectionFace
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleTile(id, pos, state) {
    protected val longEnergyStorageMap = mutableMapOf<DirectionFace, ILongEnergyStorage>()


    override fun onLoad() {
        super.onLoad()
        initCapability()
    }

    abstract fun initCapability()
}