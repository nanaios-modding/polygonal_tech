package com.nanaios.polygonal_tech.capability.energy;

import com.nanaios.polygonal_tech.capability.base.BaseCombinedCapability;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LongEnergyCombinedCapability extends BaseCombinedCapability<ILongEnergyStorage> implements ILongEnergyStorage{
    public LongEnergyCombinedCapability(@Nullable Direction side, List<ILongEnergyStorage> capabilities) {
        super(side, capabilities);
    }

    @Override
    public long getLongEnergyStored() {
        long total = 0;
        for(ILongEnergyStorage storage: capabilities) {
            // 合計値がlongの最大値を超える可能性があるため、加算時にオーバーフローをチェックする
            total = MathUtil.addExact(total, storage.getLongEnergyStored());
        }
        return total;
    }

    @Override
    public long getLongMaxEnergyStored() {
        long total = 0;
        for(ILongEnergyStorage storage: capabilities) {
            // 合計値がlongの最大値を超える可能性があるため、加算時にオーバーフローをチェックする
            total = MathUtil.addExact(total, storage.getLongMaxEnergyStored());
        }
        return total;
    }

    @Override
    public long receiveLongEnergy(long maxReceive, boolean simulate) {
        long totalReceived = 0;
        for(ILongEnergyStorage storage: capabilities) {
            if (!storage.canInput(side)) continue;
            long received = storage.receiveLongEnergy(maxReceive - totalReceived, simulate);
            totalReceived += received;
            if (totalReceived >= maxReceive) break;
        }
        return totalReceived;
    }

    @Override
    public long extractLongEnergy(long maxExtract, boolean simulate) {
        long totalExtracted = 0;
        for(ILongEnergyStorage storage: capabilities) {
            if (!storage.canOutput(side)) continue;
            long extracted = storage.extractLongEnergy(maxExtract - totalExtracted, simulate);
            totalExtracted += extracted;
            if (totalExtracted >= maxExtract) break;
        }
        return totalExtracted;
    }

    @Override
    public boolean canExtract() {
        for(ILongEnergyStorage storage: capabilities) {
            if (storage.canOutput(side)) return true;
        }
        return false;
    }

    @Override
    public boolean canReceive() {
        for(ILongEnergyStorage storage: capabilities) {
            if (storage.canInput(side)) return true;
        }
        return false;
    }
}
