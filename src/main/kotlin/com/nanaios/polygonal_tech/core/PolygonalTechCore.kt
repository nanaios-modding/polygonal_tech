package com.nanaios.polygonal_tech.core

import com.nanaios.polygonal_tech.core.registry.PolygonalTechCoreFace
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class PolygonalTechCore(context: FMLJavaModLoadingContext) {
    init {
        val bus = context.modEventBus
        PolygonalTechCoreFace.FACES.register(bus)
    }
}