package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.capability.impl.LongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

import java.util.function.LongSupplier;

public class EnergyContainer extends BaseContainer<ILongEnergyStorage> {
    public EnergyContainer(ILongEnergyStorage input, ILongEnergyStorage output) {
        super(input, output);
    }

    public static EnergyContainer create(LongSupplier capacity) {
        LongEnergyStorage base = new LongEnergyStorage(capacity, () -> true, () -> true);
        return new EnergyContainer(
                new LongEnergyStorage.InputOnly(base),
                new LongEnergyStorage.OutputOnly(base)
        );
    }
}
