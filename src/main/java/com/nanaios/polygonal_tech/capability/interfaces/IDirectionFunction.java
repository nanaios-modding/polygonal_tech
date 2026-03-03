package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IDirectionFunction {
    boolean get(@Nullable Direction side);
}
