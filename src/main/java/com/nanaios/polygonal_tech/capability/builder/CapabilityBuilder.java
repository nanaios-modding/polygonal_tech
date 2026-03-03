package com.nanaios.polygonal_tech.capability.builder;

import com.nanaios.polygonal_tech.capability.interfaces.IDirectionCapability;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class CapabilityBuilder<T extends IDirectionCapability> {
    List<T> capabilities = new ArrayList<>();

    public void addCapability(T capability) {
        capabilities.add(capability);
    }

    public Built<T> build() {
        return new Built<>();
    }

    public static class Built<T> implements ICapabilityProvider {
        @Override
        public @NotNull <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
            return null;
        }
    }
}
