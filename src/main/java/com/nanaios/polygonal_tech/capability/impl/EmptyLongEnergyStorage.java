package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

/// 空のEnergyStorage。nullチェックを回避するために使用される。
/// 常に0を返し、エネルギーの受け取りや抽出を行わない。
public class EmptyLongEnergyStorage implements ILongEnergyStorage {
    public static final EmptyLongEnergyStorage INSTANCE = new EmptyLongEnergyStorage();

    @Override
    public long getLongEnergyStored() {
        return 0;
    }

    @Override
    public long getLongMaxEnergyStored() {
        return 0;
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
