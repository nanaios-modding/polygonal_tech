package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import com.nanaios.polygonal_tech.main.tile.TestSingleTile

object PolygonalTechTileTypeRegistry {
    val MACHINE_TILES = DeferredSingleTileTypeRegister(PolygonalTech.MOD_ID)
    val TEST_MACHINE = MACHINE_TILES.register(PolygonalTechBlockRegistry.TEST_MACHINE,::TestSingleTile)
}
