package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.impl.EmptyLongEnergyStorage;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.capability.impl.LongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

import java.util.function.LongSupplier;

public class LongEnergyContainer extends BaseContainer<ILongEnergyStorage> {
    public LongEnergyContainer(ILongEnergyStorage input, ILongEnergyStorage output) {
        super(input, output, EmptyLongEnergyStorage.INSTANCE);
    }

    public static LongEnergyContainer create(LongSupplier capacity) {
        LongEnergyStorage base = new LongEnergyStorage(capacity, () -> true, () -> true);
        return new LongEnergyContainer(
                new LongEnergyStorage.InputOnly(base),
                new LongEnergyStorage.OutputOnly(base)
        );
    }
}
