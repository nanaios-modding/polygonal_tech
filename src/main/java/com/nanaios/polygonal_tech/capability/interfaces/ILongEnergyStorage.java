package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.energy.IEnergyStorage;

public interface ILongEnergyStorage extends IEnergyStorage ,IHasIOStatus{
    long getLongEnergyStored();

    long getLongMaxEnergyStored();

    long receiveLongEnergy(long maxReceive, boolean simulate);

    long extractLongEnergy(long maxExtract, boolean simulate);

    @Override
    default int receiveEnergy(int maxReceive, boolean simulate) {
        return MathUtil.longToInt(receiveLongEnergy(maxReceive, simulate));
    }

    @Override
    default int extractEnergy(int maxExtract, boolean simulate) {
        return MathUtil.longToInt(extractLongEnergy(maxExtract, simulate));
    }

    @Override
    default int getEnergyStored() {
        return MathUtil.longToInt(getLongEnergyStored());
    }

    @Override
    default int getMaxEnergyStored() {
        return MathUtil.longToInt(getLongMaxEnergyStored());
    }
}
