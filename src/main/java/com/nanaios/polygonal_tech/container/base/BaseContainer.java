package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.capability.interfaces.IHasIOStatus;
import com.nanaios.polygonal_tech.util.IOMode;
import com.nanaios.polygonal_tech.container.interfaces.IContainer;
import com.nanaios.polygonal_tech.util.interfaces.IIOMode;
import com.nanaios.polygonal_tech.util.Events;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import com.nanaios.polygonal_tech.util.interfaces.IEventHandler;
import com.nanaios.polygonal_tech.util.interfaces.IUpdatable;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public abstract class BaseContainer<T extends IHasIOStatus> implements IContainer<T>, IHasIOStatus, IEventHandler, IUpdatable {
    protected final T base;
    private final EnumMap<Direction, IIOMode> sideModes = new EnumMap<>(Direction.class);
    protected boolean active = false;
    protected Map<IEvent, List<Consumer<IEvent>>> listeners = new HashMap<>();
    private boolean isMarkUpdate = false;

    public BaseContainer(T base) {
        this.base = base;

        // 初期状態では全ての面が無効になるように設定
        for (Direction dir : Direction.values()) {
            sideModes.put(dir, IOMode.NONE);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean canInput(Direction side) {
        return active && (sideModes.get(side).equals(IOMode.INPUT) || sideModes.get(side).equals(IOMode.INPUT_OUTPUT));
    }

    @Override
    public boolean canOutput(Direction side) {
        return active && (sideModes.get(side).equals(IOMode.OUTPUT) || sideModes.get(side).equals(IOMode.INPUT_OUTPUT));
    }

    @Override
    public void setSideMode(Direction side, @NotNull IIOMode ioMode) {
        sideModes.put(side, ioMode);
        updateActive();
    }

    public boolean isMarkUpdate() {
        return isMarkUpdate;
    }

    public void setMarkUpdate(boolean mark) {
        isMarkUpdate = mark;
    }

    @Override
    public T getBase() {
        return base;
    }

    @Override
    public @Nullable T getInput(Direction side) {
        return canInput(side) ? base : null;
    }

    @Override
    public boolean canInput() {
        return base.canInput();
    }

    @Override
    public boolean canOutput() {
        return base.canOutput();
    }

    @Override
    public T getOutput(Direction side) {
        return canOutput(side) ? base : null;
    }

    /// Containerのアクティブ状態を更新する。少なくとも1つの面が入力または出力に設定されていればアクティブになる。
    public void updateActive() {
        boolean before = active;
        for (Direction side : Direction.values()) {
            if (!sideModes.get(side).equals(IOMode.NONE)) {
                active = true;
                if (!before) triggerEvent(Events.CONTAINER_UPDATE);
                return;
            }
        }
        active = false;
        if (before) triggerEvent(Events.CONTAINER_UPDATE);
    }

    public <E extends IEvent> void triggerEvent(E event) {
        List<Consumer<IEvent>> eventListeners = listeners.get(event);
        if (eventListeners != null) {
            for (Consumer<IEvent> listener : eventListeners) {
                listener.accept(event);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <E extends IEvent> void addListener(E type, Consumer<E> listener) {
        List<Consumer<IEvent>> eventListeners = listeners.getOrDefault(type, new ArrayList<>());
        eventListeners.add((Consumer<IEvent>) listener);
    }

    public <E extends IEvent> void removeListener(E type, Consumer<E> listener) {
        List<Consumer<IEvent>> eventListeners = listeners.get(type);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
}
