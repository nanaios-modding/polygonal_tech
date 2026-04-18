package com.nanaios.polygonal_tech.lib.register.multi

import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.lib.register.single.TileType
import net.minecraft.world.level.block.Block

class DeferredMachineRegister(modId: String): DeferredMultiRegister<TileType<*>, Block>(
    DeferredSingleTileTypeRegister(modId),
    DeferredBlockRegister(modId)
) {
    override val secondRegister: DeferredBlockRegister
        get() = super.secondRegister as DeferredBlockRegister
}