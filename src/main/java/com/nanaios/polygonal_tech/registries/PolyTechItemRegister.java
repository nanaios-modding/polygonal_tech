package com.nanaios.polygonal_tech.registries;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PolyTechItemRegister {
    public static final MultipleRegister<Item> ITEMS = new MultipleRegister<>(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Item> ELEMENTS = ITEMS.create();
}
