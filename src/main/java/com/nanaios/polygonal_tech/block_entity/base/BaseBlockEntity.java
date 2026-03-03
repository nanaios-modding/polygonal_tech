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

    public void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
    }

    public void clientTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        save(tag);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void serverTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            baseBlockEntity.serverTick(level, pos, state, baseBlockEntity);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void clientTicker(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof BaseBlockEntity baseBlockEntity) {
            baseBlockEntity.clientTick(level, pos, state, baseBlockEntity);
        }
    }
}