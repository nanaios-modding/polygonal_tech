package com.nanaios.polygonal_tech.capability.container;

import com.nanaios.polygonal_tech.capability.base.BaseContainer;
import com.nanaios.polygonal_tech.capability.impl.LongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

import java.util.function.LongSupplier;

public class EnergyContainer extends BaseContainer<ILongEnergyStorage> {
    public EnergyContainer(ILongEnergyStorage input, ILongEnergyStorage output, ILongEnergyStorage inputOutput) {
        super(input, output, inputOutput);
    }

    public static EnergyContainer create(LongSupplier capacity) {
        LongEnergyStorage base = new LongEnergyStorage(capacity, () -> true, () -> true);
        return new EnergyContainer(
                new LongEnergyStorage.InputOnly(base),
                new LongEnergyStorage.OutputOnly(base),
                base
        );
    }
}
