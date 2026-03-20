package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.registration.impl.DeferredItemRegister;
import com.nanaios.polygonal_tech.registration.impl.MultipleItemRegister;

public class PolygonalTechItemRegister {
    public static final MultipleItemRegister ITEMS = new MultipleItemRegister();
    public static final DeferredItemRegister ELEMENTS = ITEMS.create();
    public static final DeferredItemRegister EASTER_EGGS = ITEMS.create();
    public static final DeferredItemRegister BLOCK_ITEMS = ITEMS.create();
}
