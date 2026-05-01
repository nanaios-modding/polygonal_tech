package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.core.tile.SingleMachineTile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

class TestSingleTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleMachineTile(id, pos, state) {
    override fun initCapability() {
    }
}