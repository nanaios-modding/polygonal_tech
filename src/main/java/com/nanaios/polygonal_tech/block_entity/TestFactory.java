package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestFactory extends BaseBlockEntity<TestFactory> {

    public TestFactory(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        PolygonalTech.LOGGER.info("TestFactory created at {}", pos);
    }
}
