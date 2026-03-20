package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleFluidRegister extends MultipleRegister<Fluid> {
    public MultipleFluidRegister() {
        super(ForgeRegistries.FLUIDS);
    }

    @Override
    public DeferredFluidRegister create() {
        return new DeferredFluidRegister(createDeferredRegister());
    }
}
