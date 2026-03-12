package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.ItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import com.nanaios.polygonal_tech.util.IOMode;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import com.nanaios.polygonal_tech.util.interfaces.IEventType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseCapability implements ICapability {
    protected final List<Consumer<CapabilityUpdateEvent>> listeners = new ArrayList<>();
    protected IOMode[] ioModes = {IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE};
    protected boolean arrowInput;
    protected boolean arrowOutput;

    public BaseCapability(boolean arrowInput,boolean arrowOutput) {
        this.arrowInput = arrowInput;
        this.arrowOutput = arrowOutput;
    }

    @Override
    public boolean canInput(@Nullable Direction side) {
        if(side == null) return arrowInput;
        IOMode mode = ioModes[side.ordinal()];
        return mode == IOMode.INPUT || mode == IOMode.INPUT_OUTPUT;
    }

    @Override
    public boolean canOutput(@Nullable Direction side) {
        if(side == null) return arrowOutput;
        IOMode mode = ioModes[side.ordinal()];
        return mode == IOMode.OUTPUT || mode == IOMode.INPUT_OUTPUT;
    }

    @Override
    public void setIOMode(@NotNull Direction side, IOMode mode) {
        ioModes[side.ordinal()] = mode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends IEvent> boolean addListener(IEventType<E> type, Consumer<E> listener) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return listeners.add((Consumer<CapabilityUpdateEvent>) listener);
        }
        return false;
    }

    @Override
    public <E extends IEvent> boolean removeListener(IEventType<E> type, Consumer<E> listener) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return listeners.remove(listener);
        }
        return false;
    }

    @Override
    public <E extends IEvent> void triggerEvent(IEventType<E> type, E event) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストのリスナーにイベントを通知します。
            for (Consumer<CapabilityUpdateEvent> listener : listeners) {
                listener.accept((CapabilityUpdateEvent) event);
            }
        }
    }
}
