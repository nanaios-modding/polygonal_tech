package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public interface ILongEnergyStorage extends IEnergyStorage,ICapabilityMarker {
    String ENERGY_STORED_KEY = "stored";

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

    @Override
    default CompoundTag serializeNBT() {
        // エネルギー量をNBTタグに保存する
        CompoundTag tag = new CompoundTag();
        tag.putLong(ENERGY_STORED_KEY, getLongEnergyStored());
        return tag;
    };

    @Override
    default void deserializeNBT(CompoundTag nbt) {
        // セーブデータにエネルギーが保存されていない場合は0を使用
        long energy = nbt.contains(ENERGY_STORED_KEY)? nbt.getLong(ENERGY_STORED_KEY) : 0;

        // エネルギーを搬入して、エネルギー量を更新する
        receiveLongEnergy(energy, false);
    };
}
