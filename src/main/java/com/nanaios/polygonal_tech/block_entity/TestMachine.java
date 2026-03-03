package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class TestMachine extends BaseBlockEntity<TestMachine> {

    public TestMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @NotNull ModelData getModelData() {
        return super.getModelData();
    }
}
