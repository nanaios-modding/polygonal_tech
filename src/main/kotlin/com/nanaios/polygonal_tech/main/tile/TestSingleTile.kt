package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.lib.capability.energy.LongEnergyStorage
import com.nanaios.polygonal_tech.lib.tile.SingleMachineTile
import com.nanaios.polygonal_tech.lib.util.IOMode
import com.nanaios.polygonal_tech.lib.util.face.FaceConfigBuilder
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncIntValue
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import com.nanaios.polygonal_tech.lib.util.sync.value.bind
import com.nanaios.polygonal_tech.lib.util.sync.value.on
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class TestSingleTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleMachineTile(id, pos, state) {
    var syncInt: Int by SyncIntValue() on SyncType.ALWAYS bind this
    val energyStorage = LongEnergyStorage(0,1000, extract = true, receive = true)

    override fun onServerTick(level: Level, pos: BlockPos, state: BlockState) {
        super.onServerTick(level, pos, state)
        syncInt += 1
    }

    override fun initCapability() {
        val config = FaceConfigBuilder {
            energy(Component.literal("energy")) {
                mode = IOMode.INPUT
                cap = energyStorage
            }
        }
    }
}