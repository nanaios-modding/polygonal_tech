package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.container.impl.IOMode;
import com.nanaios.polygonal_tech.container.interfaces.IContainer;
import com.nanaios.polygonal_tech.container.interfaces.IIOMode;
import net.minecraft.core.Direction;

import java.util.EnumMap;

public abstract class BaseContainer<T> implements IContainer<T> {
    private final T input;
    private final T output;
    private final T defaultValue;

    private final EnumMap<Direction, IIOMode> sideModes = new EnumMap<>(Direction.class);

    public BaseContainer(T input,T output,T defaultValue) {
        this.input = input;
        this.output = output;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isActive() {
        for(Direction side : Direction.values()) {
            if (canInput(side) || canOutput(side)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canInput(Direction side) {
        return isActive() && (sideModes.get(side).equals(IOMode.INPUT) || sideModes.get(side).equals(IOMode.INPUT_OUTPUT));
    }

    @Override
    public boolean canOutput(Direction side) {
        return isActive() && (sideModes.get(side).equals(IOMode.OUTPUT) || sideModes.get(side).equals(IOMode.INPUT_OUTPUT));
    }

    @Override
    public void setSideMode(Direction side, IIOMode ioMode) {
        sideModes.put(side, ioMode);
    }

    @Override
    public T getInput(Direction side) {
        return canInput(side) ? input : defaultValue;
    }

    @Override
    public T getOutput(Direction side) {
        return canOutput(side) ? output : defaultValue;
    }
}
