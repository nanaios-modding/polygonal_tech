package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.network.PolygonalTechNetwork;
import com.nanaios.polygonal_tech.network.packet.ClientboundBlockEntityBufPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBlockEntity<T extends BaseBlockEntity<T>> extends BlockEntity {
    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void saveSyncData(CompoundTag tag) {
    }

    public void loadSyncData(CompoundTag tag) {
    }

    public void save(CompoundTag tag) {
    }

    public boolean serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        return false;
    }

    /// serverTickの後に呼び出されるメソッド。このメソッド内での状態の変更は避ける事が望ましい\
    /// 主にserverTickによって起きた更新を整理したり、他のBlockEntityやBlockStateに反映させるために使用される\
    /// 例えば、serverTick内で処理が完了した後に、BlockStateを更新する必要がある場合などに使用される\
    /// このメソッド内で状態を変更することは、予期しない挙動やパフォーマンスの問題を引き起こす可能性があるため、注意が必要である\
    public boolean afterServerTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        return false;
    }

    public void clientTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
    }

    public void writeSyncData(FriendlyByteBuf buf) {
    }

    public void readSyncData(FriendlyByteBuf buf) {
    }

    protected void sendSyncDataPacket() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        PolygonalTechNetwork.CHANNEL.send(
                PacketDistributor.TRACKING_CHUNK.with(() -> serverLevel.getChunkAt(worldPosition)),
                ClientboundBlockEntityBufPacket.from(this)
        );
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        save(tag);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void serverTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            boolean isChanged = baseBlockEntity.serverTick(level, pos, state, baseBlockEntity);
            isChanged |= baseBlockEntity.afterServerTick(level, pos, state, baseBlockEntity);
            if (isChanged) {
                baseBlockEntity.setChanged();
            }
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadSyncData(tag);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveSyncData(tag);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    Block.UPDATE_CLIENTS
            );
            sendSyncDataPacket();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void clientTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            baseBlockEntity.clientTick(level, pos, state, baseBlockEntity);
        }
    }
}