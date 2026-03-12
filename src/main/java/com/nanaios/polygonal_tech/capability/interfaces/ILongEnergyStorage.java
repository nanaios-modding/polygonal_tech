package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraftforge.energy.IEnergyStorage;

/// long型のエネルギー量を扱うためのインターフェース
public interface ILongEnergyStorage extends IEnergyStorage,ICapability {
    /// 現在のエネルギー量をlong型で取得します。
    long getLongEnergyStored();

    /// 最大エネルギー量をlong型で取得します。
    long getLongMaxEnergyStored();

    /// エネルギーを受け取ります。
    /// @param maxReceive 受け取るエネルギーの最大量
    /// @param simulate trueの場合、実際にはエネルギーを受け取らず、受け取れるエネルギー量をシミュレートします。
    /// @return 実際に受け取ったエネルギー量
    long receiveLongEnergy(long maxReceive, boolean simulate);

    /// エネルギーを抽出します。
    /// @param maxExtract 抽出するエネルギーの最大量
    /// @param simulate trueの場合、実際にはエネルギーを抽出せず、抽出できるエネルギー量をシミュレートします。
    /// @return 実際に抽出したエネルギー量
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
