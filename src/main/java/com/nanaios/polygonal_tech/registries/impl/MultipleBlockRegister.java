package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
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
