package com.nanaios.polygonal_tech.lang;

import net.minecraft.network.chat.MutableComponent;

public interface ILang {
    String getKey();

    MutableComponent get(Object... args);
}
