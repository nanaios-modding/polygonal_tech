package com.nanaios.polygonal_tech.registries.base;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityTypeRegister extends MultipleRegister<BlockEntityType<?>>{
    public BlockEntityTypeRegister() {
        super(ForgeRegistries.BLOCK_ENTITY_TYPES);
    }

    @Override
    public DeferredBlockEntityTypeRegister create() {
        return new DeferredBlockEntityTypeRegister(createDeferredRegister());
    }
}
