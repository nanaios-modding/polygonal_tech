package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.container.interfaces.IIOMode;

public class IOMode {
    public static final IIOMode INPUT = new Impl("input");
    public static final IIOMode OUTPUT = new Impl("output");
    public static final IIOMode INPUT_OUTPUT = new Impl("input_output");
    public static final IIOMode NONE = new Impl("none");

    public record Impl(String name) implements IIOMode { }
}
