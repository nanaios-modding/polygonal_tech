package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.capability.interfaces.IHasIOStatus;
import com.nanaios.polygonal_tech.container.interfaces.ICombinedContainer;
import com.nanaios.polygonal_tech.container.interfaces.IIOMode;
import com.nanaios.polygonal_tech.util.interfaces.IUpdatable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombinedContainer<T extends IHasIOStatus> implements ICombinedContainer<T> {
    protected final List<BaseContainer<T>> containers;
    protected final Direction side;
    protected int activeInputContainerCount = 0;
    protected int activeOutputContainerCount = 0;

    public CombinedContainer(Direction side, List<BaseContainer<T>> containers) {
        this.containers = containers;
        this.side = side;
    }

    @Override
    public int getContainerSize() {
        return containers.size();
    }

    @Override
    public boolean canInput(Direction side) {
        return activeInputContainerCount > 0;
    }

    @Override
    public boolean canOutput(Direction side) {
        return activeOutputContainerCount > 0;
    }

    public void updateActive() {
        int input = 0, output = 0;
        for (BaseContainer<T> container : containers) {
            if (container.canInput(side)) {
                input++;
            }
            if (container.canOutput(side)) {
                output++;
            }
        }
        activeInputContainerCount = input;
        activeOutputContainerCount = output;
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
    public T getBase() {
        return null;
    }

    @Override
    public boolean isActive() {
        for (BaseContainer<T> container : containers) {
            if (container.isActive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
