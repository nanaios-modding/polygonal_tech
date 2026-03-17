package com.nanaios.polygonal_tech.network.packet;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundBlockEntityBufPacket {
    private final BlockPos blockPos;
    private final byte[] payload;

    public ClientboundBlockEntityBufPacket(BlockPos blockPos, byte[] payload) {
        this.blockPos = blockPos;
        this.payload = payload;
    }

    public static ClientboundBlockEntityBufPacket from(BaseBlockEntity<?> blockEntity) {
        FriendlyByteBuf payloadBuf = new FriendlyByteBuf(Unpooled.buffer());
        blockEntity.writeSyncData(payloadBuf);

        byte[] payload = new byte[payloadBuf.readableBytes()];
        payloadBuf.getBytes(0, payload);
        payloadBuf.release();

        return new ClientboundBlockEntityBufPacket(blockEntity.getBlockPos(), payload);
    }

    public static void encode(ClientboundBlockEntityBufPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.blockPos);
        buf.writeVarInt(packet.payload.length);
        buf.writeBytes(packet.payload);
    }

    public static ClientboundBlockEntityBufPacket decode(FriendlyByteBuf buf) {
        BlockPos blockPos = buf.readBlockPos();
        int length = buf.readVarInt();
        byte[] payload = new byte[length];
        buf.readBytes(payload);
        return new ClientboundBlockEntityBufPacket(blockPos, payload);
    }

    public static void handle(ClientboundBlockEntityBufPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null || !level.hasChunkAt(packet.blockPos)) {
                return;
            }

            BlockEntity blockEntity = level.getBlockEntity(packet.blockPos);
            if (blockEntity instanceof BaseBlockEntity<?> baseBlockEntity) {
                FriendlyByteBuf payloadBuf = new FriendlyByteBuf(Unpooled.wrappedBuffer(packet.payload));
                baseBlockEntity.readSyncData(payloadBuf);
                payloadBuf.release();
            }
        });
        context.setPacketHandled(true);
    }
}

