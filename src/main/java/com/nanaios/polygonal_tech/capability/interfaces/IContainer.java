package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public interface IContainer<C> {
    @Nullable C getInput(@Nullable Direction side);
    @Nullable C getOutput(@Nullable Direction side);
    C getInternal();
}