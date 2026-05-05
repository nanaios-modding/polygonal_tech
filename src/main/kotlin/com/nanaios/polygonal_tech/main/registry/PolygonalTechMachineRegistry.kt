package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.multi.DeferredMachineRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import com.nanaios.polygonal_tech.main.tile.TestSingleTile

object PolygonalTechMachineRegistry {
    val MACHINES = DeferredMachineRegister(PolygonalTech.MOD_ID)
    val TEST_MACHINE = MACHINES.registerGuiMachine("test_machine_1",::TestSingleTile)
}