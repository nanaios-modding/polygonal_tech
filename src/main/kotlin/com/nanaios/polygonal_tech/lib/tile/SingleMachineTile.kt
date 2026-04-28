package com.nanaios.polygonal_tech.lib.tile

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

abstract class SingleMachineTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleTile(id, pos, state) {
    override fun onLoad() {
        super.onLoad()
        initCapability()
    }

    abstract fun initCapability()
}