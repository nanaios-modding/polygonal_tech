package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleItemRegister extends MultipleRegister<Item> {
    public MultipleItemRegister() {
        super(ForgeRegistries.ITEMS);
    }

    @Override
    public DeferredItemRegister create() {
        return new DeferredItemRegister(createDeferredRegister());
    }
}
