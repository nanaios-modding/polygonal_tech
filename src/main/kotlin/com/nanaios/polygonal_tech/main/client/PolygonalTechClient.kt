package com.nanaios.polygonal_tech.main.client

import com.nanaios.polygonal_tech.main.PolygonalTech
import com.nanaios.polygonal_tech.main.registry.PolygonalTechMenuTypeRegister
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod.EventBusSubscriber(modid = PolygonalTech.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechClient {
    @JvmStatic
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
        }
    }
}