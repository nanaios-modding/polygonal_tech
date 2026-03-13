package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public interface IProvider<C extends ICapability> extends ICapabilityProvider, INBTSerializable<CompoundTag> {
    void addCapability(C capability);
    void removeCapability(C capability);
}
