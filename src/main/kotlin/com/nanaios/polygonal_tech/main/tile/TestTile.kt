package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.lib.tile.TileBlockEntity
import com.nanaios.polygonal_tech.lib.util.sync.value.ALWAYS
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncIntValue
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class TestTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): TileBlockEntity(id, pos, state) {
    var syncInt:Int by SyncIntValue.ALWAYS(this)

    override fun onServerTick(level: Level, pos: BlockPos, state: BlockState) {
        super.onServerTick(level, pos, state)
        syncInt += 1
    }

    override fun onClientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.onClientTick(level, pos, state)
    }
}