package com.nanaios.polygonal_tech.lib

import com.nanaios.polygonal_tech.lib.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(modid = PolygonalTech.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechLib {
    @JvmStatic
    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {
        PolygonalTech.LOGGER.info("commonSetup called, registering network packets...")
        PolygonalTechNetwork.register()
    }
}