package com.nanaios.polygonal_tech.main.registries

import com.nanaios.polygonal_tech.lib.register.dual.DeferredBlockRegister
import com.nanaios.polygonal_tech.main.PolygonalTech

object PolygonalTechBlockRegister {
    val MACHINE_BLOCKS = DeferredBlockRegister(PolygonalTech.MOD_ID)
    val TEST_BLOCK = MACHINE_BLOCKS.register("test_block") {location ->

    }
}