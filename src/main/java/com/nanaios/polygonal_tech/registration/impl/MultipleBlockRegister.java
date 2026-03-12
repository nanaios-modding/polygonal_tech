package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleBlockRegister extends MultipleRegister<Block> {

    public MultipleBlockRegister() {
        super(ForgeRegistries.BLOCKS);
    }

    @Override
    public DeferredBlockRegister create() {
        return new DeferredBlockRegister(createDeferredRegister());
    }
}
