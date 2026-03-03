package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import com.nanaios.polygonal_tech.registries.PolyTechBlockEntityTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TestFactor extends BaseBlockEntity {
    public TestFactor(BlockPos pos, BlockState state) {
        super(PolyTechBlockEntityTypeRegister.TEST_FACTOR.get(),pos, state);
    }

    @Override
    public void save(CompoundTag tag) {

    }
}
