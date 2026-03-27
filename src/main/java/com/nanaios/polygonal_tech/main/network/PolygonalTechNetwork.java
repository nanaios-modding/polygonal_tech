package com.nanaios.polygonal_tech.main.network;

import com.nanaios.polygonal_tech.main.PolygonalTech;
import com.nanaios.polygonal_tech.main.network.packet.ClientBoundBlockEntityBufPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PolygonalTechNetwork {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            PolygonalTech.rl("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void registerMessages() {
        CHANNEL.registerMessage(
                nextPacketId(),
                ClientBoundBlockEntityBufPacket.class,
                ClientBoundBlockEntityBufPacket::encode,
                ClientBoundBlockEntityBufPacket::decode,
                ClientBoundBlockEntityBufPacket::handle
        );
    }

    private static int nextPacketId() {
        return packetId++;
    }
}

