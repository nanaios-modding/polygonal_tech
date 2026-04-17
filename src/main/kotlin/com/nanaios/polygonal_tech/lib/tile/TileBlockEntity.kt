package com.nanaios.polygonal_tech.lib.tile

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class TileBlockEntity(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState
): BlockEntity(type, pos, state), ITile {
    final override fun serverTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onServerTick(level, pos, state)
    }

    final override fun clientTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onClientTick(level, pos, state)
    }

    protected open fun onServerTick(level: Level, pos: BlockPos, state: BlockState) = Unit
    protected open fun onClientTick(level: Level, pos: BlockPos, state: BlockState) = Unit
}