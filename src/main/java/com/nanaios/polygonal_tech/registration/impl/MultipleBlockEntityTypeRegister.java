package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleBlockEntityTypeRegister extends MultipleRegister<BlockEntityType<?>> {
    public MultipleBlockEntityTypeRegister() {
        super(ForgeRegistries.BLOCK_ENTITY_TYPES);
    }

    @Override
    public DeferredBlockEntityTypeRegister create() {
        return new DeferredBlockEntityTypeRegister(createDeferredRegister());
    }
}
