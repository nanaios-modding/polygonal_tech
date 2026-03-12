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

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        // capabilitiesの数をNBTに保存する
        tag.putInt(NBT_CAPABILITY_COUNT, capabilities.size());

        // capabilitiesの各要素をNBTに保存する
        for (int i = 0; i < capabilities.size(); i++) {
            tag.put(NBT_CAPABILITY_PREFIX + i, capabilities.get(i).serializeNBT());
        }
        return tag;
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        // capabilitiesの数をNBTから読み取る
        int count = nbt.getInt(NBT_CAPABILITY_COUNT);

        // capabilitiesの各要素をNBTから読み取る
        for (int i = 0; i < count; i++) {
            CompoundTag capabilityTag = nbt.getCompound(NBT_CAPABILITY_PREFIX + i);
            if (i < capabilities.size()) {
                capabilities.get(i).deserializeNBT(capabilityTag);
            }
        }
    }
}
