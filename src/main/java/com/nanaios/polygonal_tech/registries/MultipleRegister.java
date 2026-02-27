package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MultipleRegister<T> {
    public final ResourceKey<? extends Registry<T>> registryKey;
    public final String modid;
    public final List<DeferredRegister<T>> deferredRegisters = new ArrayList<>();

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

    public DeferredRegister<T> create() {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registryKey, modid);
        deferredRegisters.add(deferredRegister);
        return deferredRegister;
    }

    public void register(IEventBus bus) {
        for (DeferredRegister<T> deferredRegister : deferredRegisters) {
            deferredRegister.register(bus);
        }
    }
}
