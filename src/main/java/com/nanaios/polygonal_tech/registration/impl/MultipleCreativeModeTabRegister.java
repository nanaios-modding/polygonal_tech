package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

public class MultipleCreativeModeTabRegister extends MultipleRegister<CreativeModeTab> {
    public MultipleCreativeModeTabRegister() {
        super(Registries.CREATIVE_MODE_TAB);
    }

    @Override
    public DeferredCreativeModeTabRegister create() {
        return new DeferredCreativeModeTabRegister(createDeferredRegister());
    }
}
