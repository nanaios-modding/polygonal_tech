package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.registries.interfaces.IDisplayable;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DeferredItemRegister extends WrapperDeferredRegister<Item> implements IDisplayable {
    public DeferredItemRegister(DeferredRegister<Item> deferredRegister) {
        super(deferredRegister);
    }

    @Override
    public void display(CreativeModeTab.Output output) {
        for(RegistryObject<Item> item : getEntries()) {
            output.accept(item.get());
        }
    }
}
