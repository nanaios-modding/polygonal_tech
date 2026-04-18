package com.nanaios.polygonal_tech.lib.network

import com.nanaios.polygonal_tech.lib.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel

object PolygonalTechNetwork {
    private const val PROTOCOL_VERSION = "1"
    private var nextPacketId = 0

    val CHANNEL: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "main"),
        { PROTOCOL_VERSION },
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )

    fun register() {
        CHANNEL.messageBuilder(SyncValuesPacket::class.java,nextPacketId++)
            .encoder(SyncValuesPacket::encode)
            .decoder(SyncValuesPacket::decode)
            .consumerMainThread(SyncValuesPacket::handle)
            .add()
    }
}