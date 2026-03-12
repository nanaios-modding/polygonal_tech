package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.jetbrains.annotations.NotNull;

/// long型の流体量を扱うためのインターフェース
public interface ILongFluidTank extends IFluidTank ,ICapability{
    String NBT_STORED_LONG_FLUID_STACK = "stored_long_fluid_stack";

    /// タンク内の流体量をlong型で取得します。
    long getFluidLongAmount();

    /// タンクの最大容量をlong型で取得します。
    long getLongCapacity();

    /// 流体を受け取ります。
    ///
    /// @param resource 受け取る流体の種類と量をLongFluidStackで指定します。
    /// @param action   受け取りのシミュレーションを行うかどうかを指定します。
    /// @return 実際に受け取った流体の量をlong型で返します。
    long fillLong(LongFluidStack resource, FluidAction action);

    /// タンク内の流体をLongFluidStackで取得します。
    ///
    /// @return タンク内の流体をLongFluidStackで返します。
    @Override
    @NotNull
    LongFluidStack getFluid();

    /// 流体を抽出します。
    ///
    /// @param maxDrain 抽出する流体の最大量をlong型で指定します。
    /// @param action   抽出のシミュレーションを行うかどうかを指定します。
    /// @return 実際に抽出された流体の種類と量をLongFluidStackで返します。
    @NotNull
    LongFluidStack drain(long maxDrain, FluidAction action);

    /// 流体を抽出します。
    ///
    /// @param resource 抽出する流体の種類と量をLongFluidStackで指定します。
    /// @param action   抽出のシミュレーションを行うかどうかを指定します。
    /// @return 実際に抽出された流体の種類と量をLongFluidStackで返します。
    @NotNull
    LongFluidStack drain(LongFluidStack resource, FluidAction action);

    @Override
    default CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put(NBT_STORED_LONG_FLUID_STACK, getFluid().writeToNBT(tag));
        return tag;
    }

    @Override
    default void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(NBT_STORED_LONG_FLUID_STACK)) {
            LongFluidStack fluidStack = LongFluidStack.loadLongFluidStackFromNBT(nbt.getCompound(NBT_STORED_LONG_FLUID_STACK));
            fillLong(fluidStack, FluidAction.EXECUTE);
        }
    }

    @NotNull
    default LongFluidStack drain(int maxDrain, FluidAction action) {
        return drain((long) maxDrain, action);
    }

    default int getFluidAmount() {
        return MathUtil.longToInt(getFluidLongAmount());
    }

    @Override
    @NotNull
    default LongFluidStack drain(FluidStack resource, FluidAction action) {
        return drain(LongFluidStack.from(resource), action);
    }

    @Override
    default int getCapacity() {
        return MathUtil.longToInt(getLongCapacity());
    }

    @Override
    default int fill(FluidStack resource, FluidAction action) {
        return MathUtil.longToInt(fillLong(LongFluidStack.from(resource), action));
    }
}