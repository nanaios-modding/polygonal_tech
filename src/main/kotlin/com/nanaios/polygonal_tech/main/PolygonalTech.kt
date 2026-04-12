package com.nanaios.polygonal_tech.main

import com.nanaios.polygonal_tech.main.registries.PolygonalTechItemRegister
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(PolygonalTech.MOD_ID)
open class PolygonalTech(context: FMLJavaModLoadingContext) {
    companion object {
        const val MOD_ID = "polygonal_tech"
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        val eventBus = context.modEventBus
        PolygonalTechItemRegister.TEST_ITEMS.register(eventBus)
    }
}