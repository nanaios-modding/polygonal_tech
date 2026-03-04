package com.nanaios.polygonal_tech.capability.provider;

import net.minecraftforge.common.capabilities.Capability;

public class CombinedCapabilityProvider<T>{
    private final Capability<T> cap;
    public CombinedCapabilityProvider(Capability<T> cap) {
        this.cap = cap;
    }
}
