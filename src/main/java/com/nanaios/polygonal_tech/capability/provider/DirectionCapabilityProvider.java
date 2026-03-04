package com.nanaios.polygonal_tech.capability.provider;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DirectionCapabilityProvider implements ICapabilityProvider {
    private final Direction side;
    public DirectionCapabilityProvider(Direction side) {
        this.side = side;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(side != this.side) return LazyOptional.empty();

        return LazyOptional.empty();
    }
}
