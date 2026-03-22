package com.nanaios.polygonal_tech.block_entity.pipe;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BasePipe<M extends BasePipe<M>> extends BaseBlockEntity<M> {
    public BasePipe(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
