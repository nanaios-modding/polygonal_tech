package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.impl.CombinedLongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

public class EnergyStorageProvider extends BaseProvider<ILongEnergyStorage, CombinedLongEnergyStorage> {
    public EnergyStorageProvider() {
        super(CombinedLongEnergyStorage::new);
    }
}