package com.nanaios.polygonal_tech.lib.registration.impl;

import com.nanaios.polygonal_tech.lib.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DeferredCreativeModeTabRegister extends WrapperDeferredRegister<CreativeModeTab> {
    private static final Map<NamedToken, RegistryObject<CreativeModeTab>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<CreativeModeTab>,NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredCreativeModeTabRegister(DeferredRegister<CreativeModeTab> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }
}
