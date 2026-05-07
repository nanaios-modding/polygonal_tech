package com.nanaios.polygonal_tech.core.network

import com.nanaios.polygonal_tech.core.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel

/**
 * MOD専用のカスタムネットワークチャンネル（[SimpleChannel]）を確立し、
 * クライアントとサーバー間の独自のパケット通信を管理・制御することを目的としたオブジェクト。
 */
object PolygonalTechNetwork {
    private const val PROTOCOL_VERSION = "1"
    private var nextPacketId = 0

    /**
     * MOD固有のネットワーク通信を行うためのメインチャンネル。
     */
    val CHANNEL: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "main"),
        { PROTOCOL_VERSION },
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )

    /**
     * MOD初期化時に呼び出され、パケットのエンコード・デコード処理やハンドラーをチャンネルに登録することを目的とするメソッド。
     */
    fun register() {
        CHANNEL.messageBuilder(SyncValuesPacket::class.java,nextPacketId++)
            .encoder(SyncValuesPacket::encode)
            .decoder(SyncValuesPacket::decode)
            .consumerMainThread(SyncValuesPacket::handle)
            .add()
    }
}