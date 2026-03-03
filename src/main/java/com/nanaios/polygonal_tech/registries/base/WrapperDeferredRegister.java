package com.nanaios.polygonal_tech.registries.base;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class WrapperDeferredRegister<T> {
    private final DeferredRegister<T> deferredRegister;

    public WrapperDeferredRegister(DeferredRegister<T> deferredRegister) {
        this.deferredRegister = deferredRegister;
    }

    public <I extends T> net.minecraftforge.registries.RegistryObject<I> register(String name, java.util.function.Supplier<? extends I> sup) {
        return deferredRegister.register(name, sup);
    }

    public Collection<RegistryObject<T>> getEntries() {
        return deferredRegister.getEntries();
    }
}
