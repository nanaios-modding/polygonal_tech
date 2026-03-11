package com.nanaios.polygonal_tech.network;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.network.base.BasePacket;
import com.nanaios.polygonal_tech.network.packet.ContainerNBTSyncPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PolygonalTechNetwork {
    public static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    PolygonalTech.rl("main"),
                    () -> PROTOCOL,
                    PROTOCOL::equals,
                    PROTOCOL::equals
            );

    private static int id = 0;

    public static void register() {
        addPacket(ContainerNBTSyncPacket.class, ContainerNBTSyncPacket::new);
    }

    public static <P extends BasePacket<P>>void addPacket(Class<P> pClass,Supplier<P> packet) {
        P instance = packet.get();
        CHANNEL.registerMessage(
                id++,
                pClass,
                instance::encode,
                instance::decode,
                BasePacket::handle
        );
    }
}
