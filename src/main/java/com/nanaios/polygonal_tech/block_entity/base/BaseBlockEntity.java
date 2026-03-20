package com.nanaios.polygonal_tech.block_entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class BaseBlockEntity<T extends BaseBlockEntity<T>> extends BlockEntity {
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

    public void sendSyncPacket() {}

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

    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T extends BlockEntity> void clientTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            baseBlockEntity.clientTick(level, pos, state, baseBlockEntity);
        }
    }
}