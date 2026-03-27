package com.nanaios.polygonal_tech.lib.registration.base;

import com.nanaios.polygonal_tech.main.PolygonalTech;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleRegister<T> {
    protected final ResourceKey<? extends Registry<T>> registryKey;
    protected final String modid;
    private final List<DeferredRegister<T>> deferredRegisters = new ArrayList<>();

    public MultipleRegister(IForgeRegistry<T> registry) {
        this(registry, PolygonalTech.MODID);
    }

    public MultipleRegister(ResourceKey<? extends Registry<T>> registryKey) {
        this(registryKey, PolygonalTech.MODID);
    }

    public MultipleRegister(IForgeRegistry<T> registry, String modid) {
        this(registry.getRegistryKey(), modid);
    }

    public MultipleRegister(ResourceKey<? extends Registry<T>> registryKey, String modid) {
        this.registryKey = registryKey;
        this.modid = modid;
    }

    /// 内部的にDeferredRegisterを作成するためのメソッド\
    /// このメソッドによって生成されたDeferredRegisterは、registerメソッドでイベントバスに登録される
    protected DeferredRegister<T> createDeferredRegister() {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registryKey, modid);
        deferredRegisters.add(deferredRegister);
        return deferredRegister;
    }

    public abstract WrapperDeferredRegister<T> create();

    public void register(IEventBus bus) {
        for (DeferredRegister<T> deferredRegister : deferredRegisters) {
            deferredRegister.register(bus);
        }
    }
}
