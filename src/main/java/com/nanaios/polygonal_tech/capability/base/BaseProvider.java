package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.capability.interfaces.IHasIOStatus;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.interfaces.ICombinedContainer;
import com.nanaios.polygonal_tech.util.impl.Events;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseProvider<T extends IHasIOStatus, C extends ICombinedContainer<T>> implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static String CONTAINERS_SIZE_KEY = "containers_size";
    public static String CONTAINER_KEY_PREFIX = "container_";

    private List<BaseContainer<T>> __containers__ = new ArrayList<>();
    private boolean locked = false;

    // 面ごとのコンテナ
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

    public BaseProvider(ContainerSupplier<T, C> containerSupplier) {
        this.up = containerSupplier.create(Direction.UP, __containers__);
        this.down = containerSupplier.create(Direction.DOWN, __containers__);
        this.north = containerSupplier.create(Direction.NORTH, __containers__);
        this.south = containerSupplier.create(Direction.SOUTH, __containers__);
        this.west = containerSupplier.create(Direction.WEST, __containers__);
        this.east = containerSupplier.create(Direction.EAST, __containers__);

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
        if (side == null) return LazyOptional.empty();
        return switch (side) {
            case UP -> up.isActive() ? upLazy.cast() : LazyOptional.empty();
            case DOWN -> down.isActive() ? downLazy.cast() : LazyOptional.empty();
            case NORTH -> north.isActive() ? northLazy.cast() : LazyOptional.empty();
            case SOUTH -> south.isActive() ? southLazy.cast() : LazyOptional.empty();
            case WEST -> west.isActive() ? westLazy.cast() : LazyOptional.empty();
            case EAST -> east.isActive() ? eastLazy.cast() : LazyOptional.empty();
        };
    }

    public void addContainer(BaseContainer<T> container) {
        if (locked) {
            PolygonalTech.LOGGER.warn("Attempted to add a container while the provider is locked. This operation is not allowed.");
            return;
        }
        __containers__.add(container);

        container.addListener(Events.CONTAINER_UPDATE, (event) -> updateActive());

        updateActive();
    }

    public void updateActive() {
        up.updateActive();
        down.updateActive();
        north.updateActive();
        south.updateActive();
        west.updateActive();
        east.updateActive();
    }

    public void removeContainer(BaseContainer<T> container) {
        if (locked) {
            PolygonalTech.LOGGER.warn("Attempted to remove a container while the provider is locked. This operation is not allowed.");
            return;
        }
        __containers__.remove(container);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putInt(CONTAINERS_SIZE_KEY, __containers__.size());

        for (int i = 0; i < __containers__.size(); i++) {
            BaseContainer<T> container = __containers__.get(i);
            tag.put(CONTAINER_KEY_PREFIX + i, container.serializeNBT());
        }

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        int size = nbt.getInt(CONTAINERS_SIZE_KEY);

        // コンテナの数がNBTに保存されている数と異なる場合、少ない方に合わせる
        if (size != __containers__.size()) {
            PolygonalTech.LOGGER.error("Container size in NBT does not match the actual container size.");
            size = Math.min(size, __containers__.size());
        }

        for (int i = 0; i < size; i++) {
            CompoundTag containerTag = nbt.getCompound(CONTAINER_KEY_PREFIX + i);
            __containers__.get(i).deserializeNBT(containerTag);
        }
    }

    /// コンテナのリストを返す\
    /// 安全のため、{@link #lock()}によってロックされるまでは空のリストを返す
    public List<BaseContainer<T>> getContainers() {
        return locked ? __containers__ : List.of();
    }

    /// インデックスに対応するコンテナを返す
    /// 安全のため、{@link #lock()}によってロックされるまではnullを返す
    public @Nullable BaseContainer<T> getContainer(int index) {
        return locked ? __containers__.get(index) : null;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
        // コンテナリストを変更不可にする
        __containers__ = Collections.unmodifiableList(__containers__);
    }

    @FunctionalInterface
    public interface ContainerSupplier<T extends IHasIOStatus, R> {
        R create(Direction side, List<BaseContainer<T>> containers);
    }
}
