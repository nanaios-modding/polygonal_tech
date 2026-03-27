package com.nanaios.polygonal_tech.main.lang;

import net.minecraft.network.chat.MutableComponent;

public interface ILang {
    String getKey();

    MutableComponent get(Object... args);
}
