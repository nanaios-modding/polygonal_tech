package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.multi.DeferredMachineRegister
import com.nanaios.polygonal_tech.main.PolygonalTech

object PolygonalTechMachineRegistry {
    val MACHINES = DeferredMachineRegister(PolygonalTech.MOD_ID)
}