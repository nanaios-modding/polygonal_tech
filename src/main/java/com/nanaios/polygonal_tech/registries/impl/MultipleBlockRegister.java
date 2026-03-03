package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class MultipleBlockRegister extends MultipleRegister<Block> {
    private final List<DeferredRegister<Item>> blockItemDeferredRegisters = new ArrayList<>();

    public MultipleBlockRegister() {
        super(ForgeRegistries.BLOCKS);
    }

    @Override
    public DeferredBlockRegister create() {
        DeferredRegister<Item> blockItemDeferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        blockItemDeferredRegisters.add(blockItemDeferredRegister);

        return new DeferredBlockRegister(
                createDeferredRegister(),
                blockItemDeferredRegister
        );
    }

    @Override
    public void register(IEventBus bus) {
        super.register(bus);
        for (DeferredRegister<Item> blockItemDeferredRegister : blockItemDeferredRegisters) {
            blockItemDeferredRegister.register(bus);
        }
    }
}
