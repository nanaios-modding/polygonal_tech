package com.nanaios.polygonal_tech.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BasePacket<P extends BasePacket<P>> {
    public static <P extends BasePacket<P>> void handle(P msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            msg.handle(msg, ctx.get());
        });

        ctx.get().setPacketHandled(true);
    }

    public abstract void handle(P msg, NetworkEvent.Context context);

    public abstract void encode(P msg, FriendlyByteBuf buffer);

    public abstract P decode(FriendlyByteBuf buffer);
}
