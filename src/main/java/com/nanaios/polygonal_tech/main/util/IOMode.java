package com.nanaios.polygonal_tech.main.util;

public enum IOMode {
    INPUT("input"), OUTPUT("output"), INPUT_OUTPUT("input_output"), NONE("none");

    private final String name;

    IOMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /// 入力可能か
    public boolean canInput() {
        return this == INPUT || this == INPUT_OUTPUT;
    }

    /// 出力可能か
    public boolean canOutput() {
        return this == OUTPUT || this == INPUT_OUTPUT;
    }
}
