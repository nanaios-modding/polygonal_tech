package com.nanaios.polygonal_tech.network;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.network.packet.ClientboundBlockEntityBufPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PolygonalTechNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            PolygonalTech.rl("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private PolygonalTechNetwork() {
    }

    public static void registerMessages() {
        CHANNEL.registerMessage(
                nextPacketId(),
                ClientboundBlockEntityBufPacket.class,
                ClientboundBlockEntityBufPacket::encode,
                ClientboundBlockEntityBufPacket::decode,
                ClientboundBlockEntityBufPacket::handle
        );
    }

    private static int nextPacketId() {
        return packetId++;
    }
}

