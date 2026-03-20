package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.MultipleRegister;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

public class MultipleFluidTypeRegister extends MultipleRegister<FluidType> {
    public MultipleFluidTypeRegister() {
        super(ForgeRegistries.Keys.FLUID_TYPES);
    }

    @Override
    public DeferredFluidTypeRegister create() {
        return new DeferredFluidTypeRegister(createDeferredRegister());
    }
}
