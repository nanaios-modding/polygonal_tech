package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.item.ElementItem;
import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechItemRegister {
    public static final MultipleRegister<Item> ITEMS = new MultipleRegister<>(ForgeRegistries.ITEMS);
    public static final WrapperDeferredRegister<Item> ELEMENTS = ITEMS.create();
    public static final WrapperDeferredRegister<Item> EASTER_EGGS = ITEMS.create();

    // 角素アイテム
    public static final RegistryObject<Item> ELEMENT_3;
    public static final RegistryObject<Item> ELEMENT_4;
    public static final RegistryObject<Item> ELEMENT_5;

    // イースターエッグアイテム
    public static final RegistryObject<Item> ELEMENT_5000;

    static {
        ELEMENT_3 = ELEMENTS.register("element_3", () -> new ElementItem(3));
        ELEMENT_4 = ELEMENTS.register("element_4", () -> new ElementItem(4));
        ELEMENT_5 = ELEMENTS.register("element_5", () -> new ElementItem(5));

        ELEMENT_5000 = EASTER_EGGS.register("element_5000", () -> new ElementItem(5000));
    }
}
