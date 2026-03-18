package com.nanaios.polygonal_tech;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum PolygonalTechLang {
    MAIN_TAB("itemGroup.polygonal_tech.main_tab"),
    FLUID_TANK_EMPTY("tooltip.polygonal_tech.fluid_tank.empty"),
    FLUID_TANK_AMOUNT("tooltip.polygonal_tech.fluid_tank.amount");

    final String key;

    PolygonalTechLang(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public MutableComponent get(Object... args) {
        return Component.translatable(key, args);
    }
}
