package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.container.interfaces.ICombinedContainer;
import com.nanaios.polygonal_tech.container.interfaces.IIOMode;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombinedContainer<T> implements ICombinedContainer<T> {
    protected final List<BaseContainer<T>> containers;
    protected final Direction side;

    public CombinedContainer(Direction side,List<BaseContainer<T>> containers) {
        this.containers = containers;
        this.side = side;
    }

    @Override
    public boolean canInput(Direction side) {
        for(BaseContainer<T> container : containers) {
            if(container.canInput(side)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(Direction side) {
        for(BaseContainer<T> container : containers) {
            if(container.canOutput(side)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setSideMode(Direction side, @NotNull IIOMode ioMode) {

    }

    @Override
    public T getInput(Direction side) {
        return null;
    }

    @Override
    public T getOutput(Direction side) {
        return null;
    }

    @Override
    public boolean isActive() {
        for(BaseContainer<T> container : containers) {
            if(container.isActive()) {
                return true;
            }
        }
        return false;
    }
}
