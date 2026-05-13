package com.nanaios.polygonal_tech.core

import com.nanaios.polygonal_tech.core.network.PolygonalTechNetwork
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(modid = PolygonalTechCore.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechCoreEventBusSubscriber {
    @JvmStatic
    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {
        PolygonalTechNetwork.register()
    }
}