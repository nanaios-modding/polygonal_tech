package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.menu.MachineMenu
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleMenuTypeRegister
import com.nanaios.polygonal_tech.main.PolygonalTech

object PolygonalTechMenuTypeRegistry {
    val MENUS = DeferredSingleMenuTypeRegister(PolygonalTech.MOD_ID)

    val THERMOELECTRIC_EXTRACTION_MACHINE = MENUS.register("thermoelectric_extraction_machine",::MachineMenu)
}