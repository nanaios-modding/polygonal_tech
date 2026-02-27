package com.nanaios.polygonal_tech;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum PolygonalTechLang {
    MAIN_TAB("itemGroup.polygonal_tech.main_tab");
    final String key;

    PolygonalTechLang(String key) {
        this.key = key;
    }

    public MutableComponent get(Object... args) {
        return Component.translatable(key, args);
    }
}
