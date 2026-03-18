package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.util.IOMode;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class CapabilityBuilder<C extends ICapability> {
    protected List<C> capabilities = new ArrayList<>();

    public void add(C capability, Direction input, Direction output) {
        capabilities.add(capability);
        capability.setIOMode(input, IOMode.INPUT);
        capability.setIOMode(output, IOMode.OUTPUT);
    }

    public void register(BaseProvider<C,?> provider) {
        for (C capability : capabilities) {
            provider.addCapability(capability);
        }
        provider.lock();
    }
}
