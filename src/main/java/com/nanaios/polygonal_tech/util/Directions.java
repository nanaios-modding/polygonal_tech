package com.nanaios.polygonal_tech.util;

import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.impl.IOMode;
import com.nanaios.polygonal_tech.container.interfaces.IIOMode;
import net.minecraft.core.Direction;

import java.util.EnumMap;

public class Directions {
    private final EnumMap<Direction, IIOMode> sideModes = new EnumMap<>(Direction.class);

    public Directions inputOnly(Direction... directions) {
        return setMode(IOMode.INPUT, directions);
    }

    public Directions outputOnly(Direction... directions) {
        return setMode(IOMode.OUTPUT, directions);
    }

    public Directions inputOutput(Direction... directions) {
        return setMode(IOMode.INPUT_OUTPUT, directions);
    }

    public Directions setMode(IIOMode ioMode, Direction... directions) {
        for(Direction direction : directions) {
            sideModes.put(direction, ioMode);
        }
        return this;
    }

    public IIOMode getMode(Direction direction) {
        return sideModes.getOrDefault(direction, IOMode.NONE);
    }

    public void applyToContainer(BaseContainer<?> container) {
        for(Direction direction : Direction.values()) {
            container.setSideMode(direction, getMode(direction));
        }
    }
}
