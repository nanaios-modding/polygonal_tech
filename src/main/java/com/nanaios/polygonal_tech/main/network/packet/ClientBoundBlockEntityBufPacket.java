package com.nanaios.polygonal_tech.main.network.packet;

import com.nanaios.polygonal_tech.main.block_entity.base.BaseMachine;
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
    public static ClientBoundBlockEntityBufPacket create(BaseMachine<?> baseMachine) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        baseMachine.writeSyncData(buf);
        Level level = baseMachine.getLevel();
        if (level == null) return null;
        return new ClientBoundBlockEntityBufPacket(baseMachine.getBlockPos(), level.dimension(), buf);
    }

    public static ClientBoundBlockEntityBufPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        ResourceKey<Level> dimension = buf.readResourceKey(Registries.DIMENSION);
        return new ClientBoundBlockEntityBufPacket(pos, dimension, buf);
    }

    public static void handle(ClientBoundBlockEntityBufPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Level level = net.minecraft.client.Minecraft.getInstance().level;
            if (level == null || !level.dimension().equals(packet.dimension)) return;

            if (level.getBlockEntity(packet.pos) instanceof BaseMachine<?> blockEntity) {
                blockEntity.readSyncData(packet.buf);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeResourceKey(dimension);
        buf.writeBytes(this.buf);
    }
}

