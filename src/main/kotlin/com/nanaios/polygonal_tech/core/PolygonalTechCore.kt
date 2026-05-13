package com.nanaios.polygonal_tech.core

import com.nanaios.polygonal_tech.core.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.RegisterEvent

@Mod.EventBusSubscriber(modid = PolygonalTech.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechCore {
    /**
     * MODの共通セットアップフェーズにて実行され、共通システムの起動を目的とするメソッド。
     * 現状は、ネットワークパケットの送受信に関連する[PolygonalTechNetwork]の登録処理を行う。
     *
     * @param event FMLのCommon Setupイベント情報を持つ[FMLCommonSetupEvent]
     */
    @JvmStatic
    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {
        PolygonalTechNetwork.register()
    }
}