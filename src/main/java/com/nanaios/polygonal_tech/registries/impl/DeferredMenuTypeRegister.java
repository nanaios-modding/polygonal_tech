package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;

public class DeferredMenuTypeRegister extends WrapperDeferredRegister<MenuType<?>> {
    public DeferredMenuTypeRegister(DeferredRegister<MenuType<?>> deferredRegister) {
        super(deferredRegister);
    }
}
