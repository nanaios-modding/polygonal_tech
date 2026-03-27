package com.nanaios.polygonal_tech.main.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PipeMode implements StringRepresentable {
    NONE("none"),
    BASIC("basic"),
    INPUT("input"),
    OUTPUT("output");

    private final String name;
    PipeMode(String name) { this.name = name; }

    @Override
    @NotNull
    public String getSerializedName() { return name; }
}