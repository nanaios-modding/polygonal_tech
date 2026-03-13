package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/// 複数のCapabilityを組み合わせたCapabilityの基底クラス。
/// このクラスは、複数のCapabilityを一つのCapabilityとして扱うための基底クラスです。
/// このクラスを元に、独自のCombinedCapabilityを作成することができます。
public abstract class BaseCombinedCapability<C extends ICapability> implements ICapability {
    public static final String NBT_CAPABILITY_COUNT = "capability_count";
    public static final String NBT_CAPABILITY_PREFIX = "capability_";

    protected boolean isActive = false;
    @Nullable
    protected final Direction side;
    protected final List<C> capabilities;

    public BaseCombinedCapability(@Nullable Direction side, List<C> capabilities) {
        this.side = side;
        this.capabilities = capabilities;
    }

    @Override
    public boolean canInput(@Nullable Direction side) {
        // 指定された方向が、このCombinedCapabilityの方向と一致しない場合は、入力できないと判断する
        if(side != this.side) return false;

        // capabilitiesの中に、指定された方向に対して入力可能なCapabilityがあるかどうかを確認する
        for (C capability : capabilities) {
            if (capability.canInput(side)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(@Nullable Direction side) {
        // 指定された方向が、このCombinedCapabilityの方向と一致しない場合は、出力できないと判断する
        if (side != this.side) return false;

        // capabilitiesの中に、指定された方向に対して出力可能なCapabilityがあるかどうかを確認する
        for (C capability : capabilities) {
            if (capability.canOutput(side)) {
                return true;
            }
        }
        return false;
    }

    /// このCombinedCapabilityの有効状態を更新する。
    public boolean updateActive() {
        // 有効な入力または出力があるかどうかを確認する
        isActive = canInput(side) || canOutput(side);
        return isActive;
    }

    /// このCombinedCapabilityが有効かどうかを返す。
    public boolean isActive() {
        return isActive;
    }
}
