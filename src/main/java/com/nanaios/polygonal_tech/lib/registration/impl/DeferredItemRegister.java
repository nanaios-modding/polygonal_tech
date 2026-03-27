package com.nanaios.polygonal_tech.lib.registration.impl;

import com.nanaios.polygonal_tech.lib.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.lib.registration.interfaces.IDisplayable;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DeferredItemRegister extends WrapperDeferredRegister<Item> implements IDisplayable {
    private static final Map<NamedToken, RegistryObject<Item>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<Item>,NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredItemRegister(DeferredRegister<Item> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    @Override
    public void display(CreativeModeTab.Output output) {
        for(RegistryObject<Item> item : getEntries()) {
            output.accept(item.get());
        }
    }
}
