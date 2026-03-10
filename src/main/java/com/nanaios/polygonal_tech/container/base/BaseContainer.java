package com.nanaios.polygonal_tech.container.base;

import com.nanaios.polygonal_tech.capability.interfaces.IHasIOStatus;
import com.nanaios.polygonal_tech.container.impl.IOMode;
import com.nanaios.polygonal_tech.container.interfaces.IContainer;
import com.nanaios.polygonal_tech.container.interfaces.IIOMode;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public abstract class BaseContainer<T extends IHasIOStatus> implements IContainer<T> , IHasIOStatus {
    protected final T base;
    private final EnumMap<Direction, IIOMode> sideModes = new EnumMap<>(Direction.class);
    protected boolean active = false;

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
        for (Direction side : Direction.values()) {
            if (!sideModes.get(side).equals(IOMode.NONE)) {
                active = true;
                return;
            }
        }
        active = false;
    }
}
