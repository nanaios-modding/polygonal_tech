package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.util.IOMode;
import com.nanaios.polygonal_tech.util.interfaces.IEventTarget;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// 最も抽象化されたCapabilityインターフェース。
public interface ICapability extends IEventTarget , INBTSerializable<CompoundTag> {
    /// このCapabilityが入力可能かどうか
    /// @param side 入力方向。nullの場合は入力が可能な任意の方向を意味する
    default boolean canInput(@Nullable Direction side) {
        return false;
    };
    /// このCapabilityが出力可能かどうか]
    /// @param side 出力方向。nullの場合は出力が可能な任意の方向を意味する
    default boolean canOutput(@Nullable Direction side) {
        return false;
    };

    /// 指定された方向のIOModeを設定する。
    /// @param side 対象の方向。
    /// @param mode 設定するIOMode。
    default void setIOMode(@NotNull Direction side, IOMode mode) {}

    @Override
    default CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    default void deserializeNBT(CompoundTag nbt) {}
}
