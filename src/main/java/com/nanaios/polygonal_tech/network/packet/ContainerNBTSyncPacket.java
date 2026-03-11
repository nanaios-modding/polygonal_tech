package com.nanaios.polygonal_tech.network.packet;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.network.base.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ContainerNBTSyncPacket extends BasePacket<ContainerNBTSyncPacket> {
    int value = 0;
    public ContainerNBTSyncPacket(int test) {
        this.value = test;
    }

    public ContainerNBTSyncPacket() {}
    @Override
    public void handle(ContainerNBTSyncPacket msg, NetworkEvent.Context context) {
        PolygonalTech.LOGGER.info("Received ContainerNBTSyncPacket with value: {}", msg.value);
    }

    @Override
    public void encode(ContainerNBTSyncPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.value);
    }

    @Override
    public ContainerNBTSyncPacket decode(FriendlyByteBuf buffer) {
        int value = buffer.readInt();
        return new ContainerNBTSyncPacket(value);
    }
}
