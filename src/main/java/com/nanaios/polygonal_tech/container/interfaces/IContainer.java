package com.nanaios.polygonal_tech.container.interfaces;

import net.minecraft.core.Direction;

public interface IContainer<T> {
    boolean canInput(Direction side);
    boolean canOutput(Direction side);
    void setSideMode(Direction side, IIOMode ioMode);
    T getInput(Direction side);
    T getOutput(Direction side);
    boolean isActive();
}
