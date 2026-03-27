package com.nanaios.polygonal_tech.lib.registration.impl;

import com.nanaios.polygonal_tech.lib.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DeferredFluidTypeRegister extends WrapperDeferredRegister<FluidType> {
    private static final Map<NamedToken, RegistryObject<FluidType>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<FluidType>, NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredFluidTypeRegister(DeferredRegister<FluidType> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }
}
