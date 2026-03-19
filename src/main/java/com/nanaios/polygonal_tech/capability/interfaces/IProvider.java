package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IProvider<C extends ICapability> extends ICapabilityProvider{
    void addCapability(C capability);
}
