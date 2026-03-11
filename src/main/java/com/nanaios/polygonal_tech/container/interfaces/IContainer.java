package com.nanaios.polygonal_tech.container.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IContainer<T> extends INBTSerializable<CompoundTag> {
    boolean canInput(Direction side);
    boolean canOutput(Direction side);
    void setSideMode(Direction side,@NotNull IIOMode ioMode);
    @Nullable T getInput(Direction side);
    @Nullable T getOutput(Direction side);
    T getBase();
    boolean isActive();
    void updateActive();
}
