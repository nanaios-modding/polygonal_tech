package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.lib.tile.TileBlockEntity
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

class TestTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): TileBlockEntity(id, pos, state) {
    override fun onServerTick(level: net.minecraft.world.level.Level, pos: BlockPos, state: BlockState) {
        super.onServerTick(level, pos, state)
        PolygonalTech.LOGGER.info("Server Tick")
    }
}