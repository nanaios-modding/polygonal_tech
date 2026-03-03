package com.nanaios.polygonal_tech.registries.base;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypeRegister extends MultipleRegister<BlockEntityType<?>>{
    public BlockEntityTypeRegister() {
        super(Registries.BLOCK_ENTITY_TYPE);
    }

    @Override
    public DeferredBlockEntityTypeRegister create() {
        return new DeferredBlockEntityTypeRegister(createDeferredRegister());
    }
}
