package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.core.capability.energy.LongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.network.sync.SyncIntValue
import com.nanaios.polygonal_tech.core.network.sync.bind
import com.nanaios.polygonal_tech.core.network.sync.on
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.tile.SingleGuiMachineTile
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState

class TestSingleTile(
    id: ResourceLocation, pos: BlockPos, state: BlockState
): SingleGuiMachineTile(id, pos, state) {
    val syncInt:Int by SyncIntValue() on SyncType.ALWAYS bind this
    val energyStorage = LongEnergyStorage(0,1000) on SyncType.ALWAYS bind this

    init {
        capability {
            energy(Component.literal("energy")) {
                mode = IOMode.INPUT_OUTPUT
                defaultFace = DirectionFace.RIGHT
                +energyStorage
            }
        }
    }
}