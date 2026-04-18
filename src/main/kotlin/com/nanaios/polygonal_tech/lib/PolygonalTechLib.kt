package com.nanaios.polygonal_tech.lib

import com.nanaios.polygonal_tech.lib.network.PolygonalTechNetwork
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechLib {
    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {
        PolygonalTechNetwork.register()
    }
}