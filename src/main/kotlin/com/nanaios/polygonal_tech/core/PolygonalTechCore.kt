package com.nanaios.polygonal_tech.core

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(PolygonalTechCore.MOD_ID)
class PolygonalTechCore(context: FMLJavaModLoadingContext) {
    companion object {
        const val MOD_ID = "polygonal_tech_core"
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        val bus = context.modEventBus
    }
}