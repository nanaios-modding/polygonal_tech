package com.nanaios.polygonal_tech.main

import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(PolygonalTech.MOD_ID)
class PolygonalTech(container: ModContainer) {
    companion object {
        const val MOD_ID = "polygonal_tech"
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
    }
}