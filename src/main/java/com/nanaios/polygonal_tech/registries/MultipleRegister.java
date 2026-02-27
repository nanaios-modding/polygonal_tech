package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MultipleRegister<T> {
    public final IForgeRegistry<T> registry;
    public final String modid;
    public final List<DeferredRegister<T>> deferredRegisters = new ArrayList<>();

    public MultipleRegister(IForgeRegistry<T> registry) {
        this(registry, PolygonalTech.MODID);
    }

    public MultipleRegister(IForgeRegistry<T> registry, String modid) {
        this.registry = registry;
        this.modid = modid;
    }

    public DeferredRegister<T> create() {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry, modid);
        deferredRegisters.add(deferredRegister);
        return deferredRegister;
    }

    public void register(IEventBus bus) {
        for (DeferredRegister<T> deferredRegister : deferredRegisters) {
            deferredRegister.register(bus);
        }
    }
}
