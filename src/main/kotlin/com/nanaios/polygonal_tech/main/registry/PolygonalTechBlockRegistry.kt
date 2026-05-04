package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.block.TileBlock
import com.nanaios.polygonal_tech.core.register.multi.DeferredBlockRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.level.block.state.BlockBehaviour

object PolygonalTechBlockRegistry {
    val MACHINE_BLOCKS = DeferredBlockRegister(PolygonalTech.MOD_ID)
}
