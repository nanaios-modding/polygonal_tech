package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class PolyTechBaseRegister<T> {
    public final List<DeferredRegister<T>> registers = new ArrayList<>();
    public final IForgeRegistry<T> registry;

    PolyTechBaseRegister(IForgeRegistry<T> registry) {
        this.registry = registry;
    }

    DeferredRegister<T> create() {
        DeferredRegister<T> register = DeferredRegister.create(registry, PolygonalTech.MODID);
        registers.add(register);
        return register;
    }

    void register(IEventBus bus) {
    }
}
