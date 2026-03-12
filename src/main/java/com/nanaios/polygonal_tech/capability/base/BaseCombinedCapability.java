package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/// 複数のCapabilityを組み合わせたCapabilityの基底クラス。
/// このクラスは、複数のCapabilityを一つのCapabilityとして扱うための基底クラスです。
/// このクラスを元に、独自のCombinedCapabilityを作成することができます。
public class BaseCombinedCapability<C extends ICapability> implements ICapability {
    @Nullable
    protected final Direction side;
    protected final List<C> capabilities;

    public BaseCombinedCapability(@Nullable Direction side, List<C> capabilities) {
        this.side = side;
        this.capabilities = capabilities;
    }

    @Override
    public boolean canInput(@Nullable Direction side) {
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
        // capabilitiesの中に、指定された方向に対して出力可能なCapabilityがあるかどうかを確認する
        for (C capability : capabilities) {
            if (capability.canOutput(side)) {
                return true;
            }
        }
        return false;
    }
}
