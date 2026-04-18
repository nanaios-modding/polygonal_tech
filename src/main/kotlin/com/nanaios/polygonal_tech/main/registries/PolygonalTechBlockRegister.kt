package com.nanaios.polygonal_tech.main.registries

import com.nanaios.polygonal_tech.lib.block.TileBlock
import com.nanaios.polygonal_tech.lib.register.multi.DeferredBlockRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.level.block.state.BlockBehaviour

object PolygonalTechBlockRegister {
    val MACHINE_BLOCKS = DeferredBlockRegister(PolygonalTech.MOD_ID)
    val TEST_MACHINE = MACHINE_BLOCKS.register("test_block") {location ->
        TileBlock(location, BlockBehaviour.Properties.of())
    }
}