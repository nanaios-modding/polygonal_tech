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
    protected C up;
    protected C down;
    protected C north;
    protected C south;
    protected C west;
    protected C east;

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

    public void initCaps() {
        upLazy = LazyOptional.of(() -> up);
        downLazy = LazyOptional.of(() -> down);
        northLazy = LazyOptional.of(() -> north);
        southLazy = LazyOptional.of(() -> south);
        westLazy = LazyOptional.of(() -> west);
        eastLazy = LazyOptional.of(() -> east);
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
}
