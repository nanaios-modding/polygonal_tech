package com.nanaios.polygonal_tech.main.client

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

/**
 * クライアントサイドでのみ実行される画面の登録やレンダリング関連の設定を集約的に行うことを目的としたオブジェクト。
 */
@Mod.EventBusSubscriber(modid = PolygonalTech.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechClient {
    /**
     * クライアントの初期化フェーズにて発火し、[MenuScreens]に対するGUI登録などをメインスレッドで安全にエンキューすることを目的とするメソッド。
     *
     * @param event クライアント用のセットアップイベント（[FMLClientSetupEvent]）
     */
    @JvmStatic
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
        }
    }
}