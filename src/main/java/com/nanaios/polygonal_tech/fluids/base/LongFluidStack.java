package com.nanaios.polygonal_tech.fluids.base;

import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class LongFluidStack extends FluidStack {
    private long longAmount;
    public static final LongFluidStack EMPTY = new LongFluidStack(Fluids.EMPTY, 0L);

    public LongFluidStack(Fluid fluid, long longAmount) {
        super(fluid, MathUtil.longToInt(longAmount));
        this.longAmount = longAmount;
    }

    public LongFluidStack(Fluid fluid, long longAmount, CompoundTag nbt) {
        super(fluid, MathUtil.longToInt(longAmount), nbt);
        this.longAmount = longAmount;
    }

    public LongFluidStack(FluidStack stack, long longAmount) {
        this(stack.getFluid(), longAmount, stack.getTag());
    }

    public static LongFluidStack from(FluidStack stack) {
        return new LongFluidStack(stack, stack.getAmount());
    }

    public static LongFluidStack loadLongFluidStackFromNBT(CompoundTag nbt) {
        if (nbt == null) return EMPTY;
        if (!nbt.contains("FluidName", 8)) return EMPTY;

        ResourceLocation fluidName = ResourceLocation.parse(nbt.getString("FluidName"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
        if (fluid == null) return EMPTY;

        if (nbt.contains("Tag", 10)) {
            return new LongFluidStack(fluid, nbt.getLong("Amount"), nbt.getCompound("Tag"));
        } else {
            return new LongFluidStack(fluid, nbt.getLong("Amount"));
        }
    }

    @Override
    public CompoundTag writeToNBT(CompoundTag nbt) {
        nbt.putString("FluidName", ForgeRegistries.FLUIDS.getKey(getFluid()).toString());
        nbt.putLong("Amount", longAmount);
        if (getTag() != null) {
            nbt.put("Tag", getTag());
        }

        return nbt;
    }

    @Override
    public void writeToPacket(FriendlyByteBuf buf) {
        buf.writeRegistryId(ForgeRegistries.FLUIDS, getFluid());
        buf.writeVarLong(getAmount());
        buf.writeNbt(getTag());
    }

    public static LongFluidStack readFromPacket(FriendlyByteBuf buf) {
        Fluid fluid = buf.readRegistryId();
        long amount = buf.readVarLong();
        CompoundTag tag = buf.readNbt();
        return fluid == Fluids.EMPTY ? EMPTY : new LongFluidStack(fluid, amount, tag);
    }

    public long getLongAmount() {
        return isEmpty() ? 0L : longAmount;
    }

    public void setAmount(long amount) {
        super.setAmount(MathUtil.longToInt(amount));
        longAmount = amount;
    }

    public void grow(long amount) {
        setAmount(longAmount + amount);
    }

    public void shrink(long amount) {
        setAmount(longAmount - amount);
    }

    @Override
    public LongFluidStack copy() {
        return new LongFluidStack(getFluid(), longAmount, getTag());
    }
}
