package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleMenuTypeRegister extends MultipleRegister<MenuType<?>> {
    public MultipleMenuTypeRegister() {
        super(ForgeRegistries.MENU_TYPES);
    }

    @Override
    public DeferredMenuTypeRegister create() {
        return new DeferredMenuTypeRegister(createDeferredRegister());
    }
}
