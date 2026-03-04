package com.nanaios.polygonal_tech.capability.container;

public abstract class BaseContainer<T> {
    private final T input;
    private final T output;
    private final T inputOutput;

    public BaseContainer(T input, T output, T inputOutput) {
        this.input = input;
        this.output = output;
        this.inputOutput = inputOutput;
    }

    public T getInput() {
        return input;
    }

    public T getOutput() {
        return output;
    }

    public T getInputOutput() {
        return inputOutput;
    }
}
