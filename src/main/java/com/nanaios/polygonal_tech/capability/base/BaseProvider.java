package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.capability.interfaces.IProvider;
import com.nanaios.polygonal_tech.event.IOModeUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseProvider<T extends ICapability,C extends BaseCombinedCapability<T>> implements IProvider<T> {
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

    @Override
    public void addCapability(T capability) {
        capability.addListener(PolygonalTechEventType.IO_MODE_UPDATE, this::updateCombinedCapabilityActive);
    }

    @Override
    public void removeCapability(T capability) {

    }

    private void updateCombinedCapabilityActive(IOModeUpdateEvent event) {

    }

    @Override
    public @NotNull <I> LazyOptional<I> getCapability(@NotNull Capability<I> cap, @Nullable Direction side) {
        if(side == null) return lazyInternal.cast();

        return switch (side) {
            case UP -> lazyUp.cast();
            case DOWN -> lazyDown.cast();
            case NORTH -> lazyNorth.cast();
            case SOUTH -> lazySouth.cast();
            case WEST -> lazyWest.cast();
            case EAST -> lazyEast.cast();
        };
    }
}
