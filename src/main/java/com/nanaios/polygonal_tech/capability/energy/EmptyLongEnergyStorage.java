package com.nanaios.polygonal_tech.capability.energy;

import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;

/// ILongEnergyStorageの空実装。常に0を返し、エネルギーの挿入や抽出を受け付けません。
/// ifPresentのデフォルト値として使用できます。
/// 例えば、エネルギー貯蔵が存在しない場合にこのクラスのインスタンスを返すことで、呼び出し側はnullチェックをせずに安全にエネルギー貯蔵を操作できます。
/// このクラスはシングルトンパターンで実装されており、INSTANCEフィールドを通じて唯一のインスタンスにアクセスできます。
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
