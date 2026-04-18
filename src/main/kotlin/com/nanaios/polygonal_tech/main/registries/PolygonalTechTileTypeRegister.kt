package com.nanaios.polygonal_tech.main.registries

import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import com.nanaios.polygonal_tech.main.tile.TestTile

object PolygonalTechTileTypeRegister {
    val MACHINE_TILES = DeferredSingleTileTypeRegister(PolygonalTech.MOD_ID)
    val TEST_MACHINE = MACHINE_TILES.register(PolygonalTechBlockRegister.TEST_MACHINE,::TestTile)
}