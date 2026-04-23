package com.nanaios.polygonal_tech.lib.fluid

import com.nanaios.polygonal_tech.lib.util.roundInt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import kotlin.math.min

open class LongFluidStack(
    val fluid: Fluid,
    protected var longAmount: Long,
    protected var nbt: CompoundTag? = null,
) {
    constructor(fluid: Fluid, longAmount: Long, nbt: CompoundTag) : this(fluid, longAmount, nbt as CompoundTag?)

    fun getAmount(): Long {
        return longAmount
    }

    fun setAmount(amount: Long) {
        longAmount = amount.coerceAtLeast(0L)
    }

    fun grow(amount: Long) {
        if (amount <= 0L) return
        longAmount = longAmount + amount
    }

    fun shrink(amount: Long) {
        if (amount <= 0L) return
        longAmount = (longAmount - amount).coerceAtLeast(0L)
    }

    fun split(amount: Long): LongFluidStack {
        if (amount <= 0L || isEmpty()) return EMPTY.copy()
        val splitAmount = min(longAmount, amount)
        longAmount -= splitAmount
        return LongFluidStack(fluid, splitAmount, nbt?.copy())
    }

    fun isEmpty(): Boolean {
        return fluid == Fluids.EMPTY || longAmount <= 0L
    }

    fun getTag(): CompoundTag? {
        return nbt
    }

    fun setTag(tag: CompoundTag?) {
        nbt = tag?.copy()
    }

    fun copy(): LongFluidStack {
        return LongFluidStack(fluid, longAmount, nbt?.copy())
    }

    fun isFluidEqual(other: LongFluidStack): Boolean {
        return fluid == other.fluid
    }

    fun isFluidStackIdentical(other: LongFluidStack): Boolean {
        return fluid == other.fluid && longAmount == other.longAmount && areTagsEqual(nbt, other.nbt)
    }

    fun toFluidStack(): FluidStack {
        if (isEmpty()) return FluidStack.EMPTY
        return FluidStack(fluid, longAmount.roundInt(), nbt?.copy())
    }

    fun writeToBuf(buf: FriendlyByteBuf) {
        buf.writeResourceLocation(BuiltInRegistries.FLUID.getKey(fluid))
        buf.writeVarLong(longAmount)
        buf.writeNbt(nbt?.copy())
    }

    fun save(tag: CompoundTag = CompoundTag()): CompoundTag {
        if (isEmpty()) return tag

        tag.putString(KEY_FLUID_NAME, BuiltInRegistries.FLUID.getKey(fluid).toString())
        tag.putLong(KEY_AMOUNT, longAmount)

        val copiedTag = nbt
        if (copiedTag != null && !copiedTag.isEmpty) {
            tag.put(KEY_TAG, copiedTag.copy())
        }

        return tag
    }

    companion object {
        const val KEY_FLUID_NAME: String = "FluidName"
        const val KEY_AMOUNT: String = "Amount"
        const val KEY_TAG: String = "Tag"

        @JvmField
        val EMPTY: LongFluidStack = LongFluidStack(Fluids.EMPTY, 0L)

        @JvmStatic
        fun of(fluid: Fluid, amount: Long, tag: CompoundTag? = null): LongFluidStack {
            if (fluid == Fluids.EMPTY || amount <= 0L) return EMPTY.copy()
            return LongFluidStack(fluid, amount, tag?.copy())
        }

        @JvmStatic
        fun fromFluidStack(stack: FluidStack): LongFluidStack {
            if (stack.isEmpty) return EMPTY.copy()
            return LongFluidStack(stack.fluid, stack.amount.toLong(), stack.tag?.copy())
        }

        @JvmStatic
        fun readFromBuf(buf: FriendlyByteBuf): LongFluidStack {
            val fluidId = buf.readResourceLocation()
            val fluid = BuiltInRegistries.FLUID.getOptional(fluidId).orElse(Fluids.EMPTY)
            val amount = buf.readVarLong()
            val tag = buf.readNbt()
            return of(fluid, amount, tag)
        }

        @JvmStatic
        fun load(tag: CompoundTag): LongFluidStack {
            if (!tag.contains(KEY_FLUID_NAME)) return EMPTY.copy()

            val fluidId = ResourceLocation.tryParse(tag.getString(KEY_FLUID_NAME)) ?: return EMPTY.copy()
            val fluid = BuiltInRegistries.FLUID.getOptional(fluidId).orElse(Fluids.EMPTY)
            if (fluid == Fluids.EMPTY) return EMPTY.copy()

            val amount = tag.getLong(KEY_AMOUNT)
            if (amount <= 0L) return EMPTY.copy()

            val copiedTag = if (tag.contains(KEY_TAG, CompoundTag.TAG_COMPOUND.toInt())) {
                tag.getCompound(KEY_TAG).copy()
            } else {
                null
            }

            return LongFluidStack(fluid, amount, copiedTag)
        }

        @JvmStatic
        fun areTagsEqual(first: CompoundTag?, second: CompoundTag?): Boolean {
            if (first == null && second == null) return true
            if (first == null || second == null) return false
            return first == second
        }
    }
}
