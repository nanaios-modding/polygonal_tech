package com.nanaios.polygonal_tech.lib.tile

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

abstract class MachineBase(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleTile(id, pos, state) {

}