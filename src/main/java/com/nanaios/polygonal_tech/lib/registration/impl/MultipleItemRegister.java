package com.nanaios.polygonal_tech.lib.registration.impl;

import com.nanaios.polygonal_tech.lib.registration.base.MultipleRegister;
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
