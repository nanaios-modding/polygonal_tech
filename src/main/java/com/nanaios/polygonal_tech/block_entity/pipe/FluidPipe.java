package com.nanaios.polygonal_tech.block_entity.pipe;

import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FluidPipe extends BasePipe<FluidPipe> {
    public FluidPipe(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.FLUID_PIPE.get(), pos, state);
    }
}
