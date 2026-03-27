package com.nanaios.polygonal_tech.main.capability.interfaces;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IProvider<C extends ICapability> extends ICapabilityProvider{
    void addCapability(C capability);
}
