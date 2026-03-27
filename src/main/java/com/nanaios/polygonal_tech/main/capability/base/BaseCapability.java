package com.nanaios.polygonal_tech.main.capability.base;

import com.nanaios.polygonal_tech.main.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.main.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.main.event.IOModeUpdateEvent;
import com.nanaios.polygonal_tech.main.event.PolygonalTechEventType;
import com.nanaios.polygonal_tech.main.util.IOMode;
import com.nanaios.polygonal_tech.main.event.interfaces.IEvent;
import com.nanaios.polygonal_tech.main.event.interfaces.IEventType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// ICapabilityの基本実装。入出力の管理とイベントリスナーの管理を提供します。
/// このクラスは、特定の機能を持つCapabilityの基底クラスとして使用されることを想定しています。
public abstract class BaseCapability implements ICapability {
    protected final List<Consumer<CapabilityUpdateEvent>> capabilityUpdateEventListener = new ArrayList<>();
    protected final List<Consumer<IOModeUpdateEvent>> ioModeUpdateEventListener = new ArrayList<>();
    protected IOMode[] ioModes = {IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE, IOMode.NONE};
    protected boolean arrowInput;
    protected boolean arrowOutput;
    /// 注意；クライアント側ではこの値は意味を持ちません。サーバー側でのみ、Capabilityの状態が変更されたことを示すために使用されます。
    protected boolean isMarkUpdate;

    public BaseCapability(boolean arrowInput,boolean arrowOutput) {
        this.arrowInput = arrowInput;
        this.arrowOutput = arrowOutput;
    }

    @Override
    public void setMarkUpdate(boolean markUpdate) {
        isMarkUpdate = markUpdate;
    }

    @Override
    public boolean isMarkUpdate() {
        return isMarkUpdate;
    }

    @Override
    public boolean canInput(@Nullable Direction side) {
        if(!arrowInput) return false;
        if(side == null) return true;
        IOMode mode = ioModes[side.ordinal()];
        return mode == IOMode.INPUT || mode == IOMode.INPUT_OUTPUT;
    }

    @Override
    public boolean canOutput(@Nullable Direction side) {
        if(!arrowOutput) return false;
        if(side == null) return true;
        IOMode mode = ioModes[side.ordinal()];
        return mode == IOMode.OUTPUT || mode == IOMode.INPUT_OUTPUT;
    }

    @Override
    public void setIOMode(@NotNull Direction side, IOMode mode) {
        IOMode oldMode = ioModes[side.ordinal()];
        ioModes[side.ordinal()] = mode;
        if(oldMode != mode) {
            // IOModeが変更された場合、IOModeUpdateEventをトリガーします。
            triggerEvent(PolygonalTechEventType.IO_MODE_UPDATE, new IOModeUpdateEvent(side));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends IEvent> boolean addListener(IEventType<E> type, Consumer<E> listener) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return capabilityUpdateEventListener.add((Consumer<CapabilityUpdateEvent>) listener);
        } else if (type == PolygonalTechEventType.IO_MODE_UPDATE) {
            // この時点でEはIOModeUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return ioModeUpdateEventListener.add((Consumer<IOModeUpdateEvent>) listener);
        }
        return false;
    }

    @Override
    public <E extends IEvent> boolean removeListener(IEventType<E> type, Consumer<E> listener) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return capabilityUpdateEventListener.remove(listener);
        } else if (type == PolygonalTechEventType.IO_MODE_UPDATE) {
            // この時点でEはIOModeUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return ioModeUpdateEventListener.remove(listener);
        }
        return false;
    }

    @Override
    public <E extends IEvent> void triggerEvent(IEventType<E> type, E event) {
        if (type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストのリスナーにイベントを通知します。
            for (Consumer<CapabilityUpdateEvent> listener : capabilityUpdateEventListener) {
                listener.accept((CapabilityUpdateEvent) event);
            }
            setMarkUpdate(true);
        } else if (type == PolygonalTechEventType.IO_MODE_UPDATE) {
            // この時点でEはIOModeUpdateEventであることが保証されているため、キャストしてリストのリスナーにイベントを通知します。
            for (Consumer<IOModeUpdateEvent> listener : ioModeUpdateEventListener) {
                listener.accept((IOModeUpdateEvent) event);
            }
            setMarkUpdate(true);
        }
    }
}
