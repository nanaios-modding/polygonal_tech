package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.network.PolygonalTechNetwork;
import com.nanaios.polygonal_tech.network.packet.ClientBoundBlockEntityBufPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class BaseBlockEntity<T extends BaseBlockEntity<T>> extends BlockEntity {
    private boolean markedForSync = false;

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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

    /// クライアントに同期するデータを書き込むためのメソッド。これをオーバーライドして、クライアントに送信したいデータをbufに書き込むことができる。\
    public void writeSyncData(FriendlyByteBuf buf) {
    }

    /// クライアントから受け取った同期データを読み込むためのメソッド。これをオーバーライドして、クライアントから送信されたデータをbufから読み取ることができる。\
    public void readSyncData(FriendlyByteBuf buf) {
    }

    public void sendSyncPacket() {
        if (markedForSync && level != null && !level.isClientSide) {
            ClientBoundBlockEntityBufPacket packet = ClientBoundBlockEntityBufPacket.create(this);
            if (packet == null) return;
            PolygonalTechNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), packet);
            markedForSync = false;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        save(tag);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T extends BlockEntity> void serverTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            boolean isChanged = baseBlockEntity.serverTick(level, pos, state, baseBlockEntity);
            isChanged |= baseBlockEntity.afterServerTick(level, pos, state, baseBlockEntity);
            if (isChanged) {
                // BlockEntityの状態が変更されたことをマークする。
                baseBlockEntity.setChanged();
                // クライアントに状態の変更を通知するためのパケットを送信する。
                baseBlockEntity.sendSyncPacket();
            }
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        markedForSync = true;
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T extends BlockEntity> void clientTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            baseBlockEntity.clientTick(level, pos, state, baseBlockEntity);
        }
    }
}