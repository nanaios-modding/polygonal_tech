package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.impl.EmptyLongEnergyStorage;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.capability.impl.LongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import net.minecraft.nbt.CompoundTag;

import java.util.function.LongSupplier;

public class LongEnergyContainer extends BaseContainer<ILongEnergyStorage> implements ILongEnergyStorage {
    public static String STORED_ENERGY_KEY = "stored";

    public LongEnergyContainer(ILongEnergyStorage base,ILongEnergyStorage input, ILongEnergyStorage output) {
        super(base,input, output, EmptyLongEnergyStorage.INSTANCE);
    }

    public static LongEnergyContainer create(LongSupplier capacity) {
        LongEnergyStorage base = new LongEnergyStorage(capacity, () -> true, () -> true);
        return new LongEnergyContainer(
                base,
                new LongEnergyStorage.InputOnly(base),
                new LongEnergyStorage.OutputOnly(base)
        );
    }

    @Override
    public long getLongEnergyStored() {
        return base.getLongEnergyStored();
    }

    @Override
    public long getLongMaxEnergyStored() {
        return base.getLongMaxEnergyStored();
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        return base.receiveLongEnergy(maxReceive, simulate);
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        return base.extractLongEnergy(maxExtract, simulate);
    }

    @Override
    public boolean canExtract() {
        return base.canExtract();
    }

    @Override
    public boolean canReceive() {
        return base.canReceive();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putLong(STORED_ENERGY_KEY, getLongEnergyStored());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(STORED_ENERGY_KEY)) {
            long storedEnergy = nbt.getLong(STORED_ENERGY_KEY);
            receiveLongEnergy(storedEnergy, false);
        }
    }
}
