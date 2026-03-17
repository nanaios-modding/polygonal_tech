package com.nanaios.polygonal_tech.network.packet;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public record ClientBoundBlockEntityBufPacket(BlockPos pos, ResourceKey<Level> dimension, FriendlyByteBuf buf) {
    @Nullable
    public static ClientBoundBlockEntityBufPacket create(BaseBlockEntity<?> baseBlockEntity) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        baseBlockEntity.writeSyncData(buf);
        Level level = baseBlockEntity.getLevel();
        if (level == null) return null;
        return new ClientBoundBlockEntityBufPacket(baseBlockEntity.getBlockPos(), level.dimension(), buf);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeResourceKey(dimension);
        buf.writeBytes(this.buf);
    }

    public static ClientBoundBlockEntityBufPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        ResourceKey<Level> dimension = buf.readResourceKey(Registries.DIMENSION);
        return new ClientBoundBlockEntityBufPacket(pos, dimension, buf);
    }

    public static void handle(ClientBoundBlockEntityBufPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->{
            Level level = net.minecraft.client.Minecraft.getInstance().level;
            if (level == null || !level.dimension().equals(packet.dimension)) return;

            if (level.getBlockEntity(packet.pos) instanceof BaseBlockEntity<?> blockEntity) {
                blockEntity.readSyncData(packet.buf);
            }
        });
    }
}

