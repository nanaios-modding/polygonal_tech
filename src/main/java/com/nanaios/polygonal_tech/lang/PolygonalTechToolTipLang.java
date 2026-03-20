package com.nanaios.polygonal_tech.lang;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum PolygonalTechToolTipLang implements ILang{
    FLUID_TANK_EMPTY("fluid_tank.empty"),
    FLUID_TANK_AMOUNT("fluid_tank.amount");

    final String key;

    PolygonalTechToolTipLang(String key) {
        this.key = "tooltip."+ PolygonalTech.MODID + "."+ key;
    }

    public String getKey() {
        return key;
    }

    public MutableComponent get(Object... args) {
        return Component.translatable(key, args);
    }
}
