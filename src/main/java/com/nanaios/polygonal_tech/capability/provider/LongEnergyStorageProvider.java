package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.container.CombinedLongEnergyContainer;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

public class LongEnergyStorageProvider extends BaseProvider<ILongEnergyStorage, CombinedLongEnergyContainer> {
    public static String NBT_KEY = "long_energy_storage";

    public LongEnergyStorageProvider() {
        super(CombinedLongEnergyContainer::new);
    }
}