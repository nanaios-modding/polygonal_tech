package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.container.impl.CombinedLongEnergyContainer;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

public class LongEnergyStorageProvider extends BaseProvider<ILongEnergyStorage, CombinedLongEnergyContainer> {
    public LongEnergyStorageProvider() {
        super(CombinedLongEnergyContainer::new);
    }
}