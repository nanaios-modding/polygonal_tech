package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.multi.DeferredMachineRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import com.nanaios.polygonal_tech.main.tile.ThermoelectricExtractionMachine
import net.minecraft.world.level.block.Block

object PolygonalTechMachineRegistry {
    val MACHINES = DeferredMachineRegister(PolygonalTech.MOD_ID)

    val THERMOELECTRIC_EXTRACTION_MACHINE = MACHINES.registerGuiMachine("thermoelectric_extraction_machine") { id, pos, state ->
        ThermoelectricExtractionMachine(id, pos, state)
    }
}