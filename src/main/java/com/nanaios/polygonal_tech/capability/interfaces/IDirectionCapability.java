package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.core.Direction;

public interface IDirectionCapability {
    boolean canReceive(Direction side);

    boolean canExtract(Direction side);
}
