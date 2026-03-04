package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapabilityMarker;

public abstract class BaseContainer<T extends ICapabilityMarker> {
    private final T input;
    private final T output;

    public BaseContainer(T input, T output) {
        this.input = input;
        this.output = output;
    }

    public T getInput() {
        return input;
    }

    public T getOutput() {
        return output;
    }


    public enum IOType {
        INPUT,
        OUTPUT
    }
}
