package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.capability.interfaces.IProvider;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.event.IOModeUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseProvider<T extends ICapability, C extends BaseCombinedCapability<T>> implements IProvider<T> {
    public static final String NBT_CAPABILITY_COUNT = "capability_count";
    public static final String NBT_CAPABILITY_PREFIX = "capability_";
    protected List<T> capabilities = new ArrayList<>();
    protected Consumer<CapabilityUpdateEvent> capabilityUpdateListener;
    protected C internal;
    protected C up;
    protected C down;
    protected C north;
    protected C south;
    protected C west;
    protected C east;
    protected LazyOptional<C> lazyInternal;
    protected LazyOptional<C> lazyUp;
    protected LazyOptional<C> lazyDown;
    protected LazyOptional<C> lazyNorth;
    protected LazyOptional<C> lazySouth;
    protected LazyOptional<C> lazyWest;
    protected LazyOptional<C> lazyEast;
    private boolean isLocked = false;

    public BaseProvider(CombinedCapabilityFactory<T, C> factory, Consumer<CapabilityUpdateEvent> capabilityUpdateListener) {
        internal = factory.create(null, capabilities);
        up = factory.create(Direction.UP, capabilities);
        down = factory.create(Direction.DOWN, capabilities);
        north = factory.create(Direction.NORTH, capabilities);
        south = factory.create(Direction.SOUTH, capabilities);
        west = factory.create(Direction.WEST, capabilities);
        east = factory.create(Direction.EAST, capabilities);

        this.capabilityUpdateListener = capabilityUpdateListener;

        initCaps();
    }

    @Override
    public void addCapability(T capability) {
        int index = capabilities.size();
        capability.addListener(PolygonalTechEventType.IO_MODE_UPDATE, this::updateCombinedCapabilityActive);
        capability.addListener(PolygonalTechEventType.CAPABILITY_UPDATE, (event) -> updateCapability(event, index));
        capabilities.add(capability);
    }

    /// LazyOptionalを初期化するためのヘルパーメソッド。コンストラクタで呼び出され、各combined capabilityに対応するLazyOptionalを生成します。
    private void initCaps() {
        lazyInternal = LazyOptional.of(() -> internal);
        lazyUp = LazyOptional.of(() -> up);
        lazyDown = LazyOptional.of(() -> down);
        lazyNorth = LazyOptional.of(() -> north);
        lazySouth = LazyOptional.of(() -> south);
        lazyWest = LazyOptional.of(() -> west);
        lazyEast = LazyOptional.of(() -> east);
    }

    /// capabilityを復活させるためのヘルパーメソッド。invalidateCapsで無効化されたcapabilityを再度有効にするために使用されます。通常、BlockEntityのreviveCapsメソッド内で呼び出されます。
    public void reviveCaps() {
        lazyInternal = updateLazy(internal, lazyInternal);
        lazyUp = updateLazy(up, lazyUp);
        lazyDown = updateLazy(down, lazyDown);
        lazyNorth = updateLazy(north, lazyNorth);
        lazySouth = updateLazy(south, lazySouth);
        lazyWest = updateLazy(west, lazyWest);
        lazyEast = updateLazy(east, lazyEast);
    }

    /// capabilityを無効化するためのヘルパーメソッド。通常、BlockEntityのinvalidateCapsメソッド内で呼び出され、すべてのcapabilityを無効にします。
    public void invalidateCaps() {
        lazyInternal.invalidate();
        lazyUp.invalidate();
        lazyDown.invalidate();
        lazyNorth.invalidate();
        lazySouth.invalidate();
        lazyWest.invalidate();
        lazyEast.invalidate();
    }

    /// capabilityの状態を更新し、必要に応じてLazyOptionalを更新するヘルパーメソッド。
    /// このメソッドは、capabilityの状態が変化した場合にLazyOptionalを更新するために使用されます。状態が変化していない場合は、現在のLazyOptionalをそのまま返します。
    /// これにより、状態が変化していない場合の不必要なLazyOptionalの再生成を防止し、パフォーマンスを向上させることができます。
    /// このメソッドは、updateCombinedCapabilityActive内で使用され、combined capabilityの状態が変化した場合に対応するLazyOptionalを更新します。
    /// 例えば、combined capabilityが有効になった場合は、新しいLazyOptionalを生成し、無効になった場合はLazyOptionalを空にします。
    ///
    /// @param capability 状態を更新するcapability
    /// @param nowLazy    現在のLazyOptional
    /// @return 更新されたLazyOptional。状態が変化していない場合は、現在のLazyOptionalを返します。
    private LazyOptional<C> updateLazy(C capability, LazyOptional<C> nowLazy) {
        // capabilityの状態を更新する
        boolean oldActive = capability.isActive();
        boolean newActive = capability.updateActive();

        // 状態が変化していない場合は更新の必要がないため、現在のLazyOptionalを返します。
        if (oldActive == newActive) return nowLazy;

        // 状態が変化している場合は、LazyOptionalを更新します。
        nowLazy.invalidate();
        if (newActive) {
            return LazyOptional.of(() -> capability);
        } else {
            return LazyOptional.empty();
        }
    }

    public List<T> getCapabilities() {
        if (!isLocked) return List.of();
        return capabilities;
    }

    private void updateCombinedCapabilityActive(IOModeUpdateEvent event) {
        // sideがnullの場合はinternalのみ更新する
        if (event.side() == null) {
            lazyInternal = updateLazy(internal, lazyInternal);
            return;
        }

        // sideに応じて対応するcombined capabilityを更新する
        // 更新後、combined capabilityが有効かどうかを確認し、有効な場合はLazyOptionalを再生成し、無効な場合はLazyOptionalを空にします。
        // これにより、getCapability内での処理を簡略化するとともに、有効でないcombined capabilityに対するアクセスを防止します。
        switch (event.side()) {
            case UP -> lazyUp = updateLazy(up, lazyUp);
            case DOWN -> lazyDown = updateLazy(down, lazyDown);
            case NORTH -> lazyNorth = updateLazy(north, lazyNorth);
            case SOUTH -> lazySouth = updateLazy(south, lazySouth);
            case WEST -> lazyWest = updateLazy(west, lazyWest);
            case EAST -> lazyEast = updateLazy(east, lazyEast);
        }
    }

    private void updateCapability(CapabilityUpdateEvent event, int index) {
        // capabilityの更新を通知
        this.capabilityUpdateListener.accept(event);
    }

    @Override
    public @NotNull <I> LazyOptional<I> getCapability(@NotNull Capability<I> cap, @Nullable Direction side) {
        if (side == null) return lazyInternal.cast();

        return switch (side) {
            case UP -> lazyUp.cast();
            case DOWN -> lazyDown.cast();
            case NORTH -> lazyNorth.cast();
            case SOUTH -> lazySouth.cast();
            case WEST -> lazyWest.cast();
            case EAST -> lazyEast.cast();
        };
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        // capabilitiesの数をNBTに保存する
        tag.putInt(NBT_CAPABILITY_COUNT, capabilities.size());

        // capabilitiesの各要素をNBTに保存する
        for (int i = 0; i < capabilities.size(); i++) {
            tag.put(NBT_CAPABILITY_PREFIX + i, capabilities.get(i).serializeNBT());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        // capabilitiesの数をNBTから読み取る
        int count = nbt.getInt(NBT_CAPABILITY_COUNT);

        // capabilitiesの各要素をNBTから読み取る
        for (int i = 0; i < count; i++) {
            CompoundTag capabilityTag = nbt.getCompound(NBT_CAPABILITY_PREFIX + i);
            capabilities.get(i).deserializeNBT(capabilityTag);
        }
    }

    /// providerをロックし、新規のcapabilityの追加を防止するためのメソッド。
    /// ロックにより予期せぬcapabilityの追加を防止するとともに、サイズの固定をします。
    /// これにより、メモリ効率の向上や、capabilityの同期を容易にします
    public void lock() {
        // 不変リストに変更
        capabilities = Collections.unmodifiableList(capabilities);
        isLocked = true;
    }

    /// combined capabilityを生成するためのファクトリインターフェース
    @FunctionalInterface
    public interface CombinedCapabilityFactory<T extends ICapability, C extends BaseCombinedCapability<T>> {
        C create(Direction side, List<T> capabilities);
    }
}
