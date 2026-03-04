package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseMachine;
import com.nanaios.polygonal_tech.registries.PolyTechBlockEntityTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PhotolysisMachine extends BaseMachine<PhotolysisMachine> {
    public PhotolysisMachine(BlockPos pos, BlockState state) {
        super(PolyTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE.get(), pos, state);
    }
}
