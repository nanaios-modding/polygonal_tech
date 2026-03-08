package com.nanaios.polygonal_tech.container.interfaces;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public interface IContainer<T> extends INBTSerializable<CompoundTag> {
    boolean canInput(Direction side);
    boolean canOutput(Direction side);
    void setSideMode(Direction side,@NotNull IIOMode ioMode);
    T getInput(Direction side);
    T getOutput(Direction side);
    boolean isActive();
}
