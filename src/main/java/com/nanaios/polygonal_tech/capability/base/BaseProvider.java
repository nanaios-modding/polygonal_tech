package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapabilityMarker;
import com.nanaios.polygonal_tech.capability.interfaces.ICombinedCapability;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BaseProvider<T extends ICapabilityMarker,C extends ICombinedCapability<T>> implements ICapabilityProvider {
    // 面のCapabilityインスタンス
    protected C up;
    protected C down;
    protected C north;
    protected C south;
    protected C west;
    protected C east;

    // 面のLazyOptional
    protected LazyOptional<C> upLazy;
    protected LazyOptional<C> downLazy;
    protected LazyOptional<C> northLazy;
    protected LazyOptional<C> southLazy;
    protected LazyOptional<C> westLazy;
    protected LazyOptional<C> eastLazy;

    public BaseProvider(Supplier<C> capabilitySupplier) {
        this.up = capabilitySupplier.get();
        this.down = capabilitySupplier.get();
        this.north = capabilitySupplier.get();
        this.south = capabilitySupplier.get();
        this.west = capabilitySupplier.get();
        this.east = capabilitySupplier.get();

        initCaps();
    }

    /// LazyOptionalの初期化
    private void initCaps() {
        upLazy = LazyOptional.of(() -> up);
        downLazy = LazyOptional.of(() -> down);
        northLazy = LazyOptional.of(() -> north);
        southLazy = LazyOptional.of(() -> south);
        westLazy = LazyOptional.of(() -> west);
        eastLazy = LazyOptional.of(() -> east);
    }

    public void reviveCaps() {
        initCaps();
    }

    public void invalidateCaps() {
        upLazy.invalidate();
        downLazy.invalidate();
        northLazy.invalidate();
        southLazy.invalidate();
        westLazy.invalidate();
        eastLazy.invalidate();
    }

    @Override
    public @NotNull <I> LazyOptional<I> getCapability(@NotNull Capability<I> cap, @Nullable Direction side) {
        if(side == null) return LazyOptional.empty();

        return switch (side) {
            case UP -> upLazy.cast();
            case DOWN -> downLazy.cast();
            case NORTH -> northLazy.cast();
            case SOUTH -> southLazy.cast();
            case WEST -> westLazy.cast();
            case EAST -> eastLazy.cast();
        };
    }

    /// Capabilityを指定した面に追加
    /// @param side 追加する面
    /// @param capability 追加するCapability
    public void add(Direction side, T capability) {
        switch (side) {
            case UP -> up.add(capability);
            case DOWN -> down.add(capability);
            case NORTH -> north.add(capability);
            case SOUTH -> south.add(capability);
            case WEST -> west.add(capability);
            case EAST -> east.add(capability);
        }
    }

    /// containerのCapabilityを指定した面に追加
    /// @param side 追加する面
    /// @param container 追加するCapabilityを持つcontainer
    /// @param ioType 追加するCapabilityの種類
    public void add(Direction side, BaseContainer<T> container, BaseContainer.IOType ioType) {
        switch (ioType) {
            case INPUT -> add(side, container.getInput());
            case OUTPUT -> add(side, container.getOutput());
            case INPUT_OUTPUT -> add(side, container.getInputOutput());
        }
    }

    /// Capabilityを指定した面から削除
    /// @param side 削除する面
    /// @param capability 削除するCapability
    public void remove(Direction side, T capability) {
        switch (side) {
            case UP -> up.remove(capability);
            case DOWN -> down.remove(capability);
            case NORTH -> north.remove(capability);
            case SOUTH -> south.remove(capability);
            case WEST -> west.remove(capability);
            case EAST -> east.remove(capability);
        }
    }

    /// containerのCapabilityを指定した面から削除
    /// @param side 削除する面
    /// @param container 削除するCapabilityを持つcontainer
    /// @param ioType 削除するCapabilityの種類
    public void remove(Direction side, BaseContainer<T> container, BaseContainer.IOType ioType) {
        switch (ioType) {
            case INPUT -> remove(side, container.getInput());
            case OUTPUT -> remove(side, container.getOutput());
            case INPUT_OUTPUT -> remove(side, container.getInputOutput());
        }
    }
}
