package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.lib.capability.energy.LongEnergyStorage
import com.nanaios.polygonal_tech.lib.tile.SingleTile
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncIntValue
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import com.nanaios.polygonal_tech.lib.util.sync.value.bind
import com.nanaios.polygonal_tech.lib.util.sync.value.on
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class TestSingleTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleTile(id, pos, state) {
    var syncInt:Int by SyncIntValue() on SyncType.ALWAYS bind this
    val energy = LongEnergyStorage(0,10000, extract = true, receive = true) on SyncType.ALWAYS bind this

    override fun onServerTick(level: Level, pos: BlockPos, state: BlockState) {
        super.onServerTick(level, pos, state)
        syncInt += 1
    }
}