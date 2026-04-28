package com.nanaios.polygonal_tech.lib.fluid

import com.nanaios.polygonal_tech.lib.util.roundInt
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.registries.ForgeRegistries

open class LongFluidStack(
    private val rawFluid: Fluid,
    private var amount: Long,
    private var tag: CompoundTag? = null,
) {
    private var isEmpty: Boolean = rawFluid == Fluids.EMPTY || amount <= 0L

    val fluid: Fluid
        get() = if (isEmpty) Fluids.EMPTY else rawFluid

    fun getRawFluid(): Fluid {
        return rawFluid
    }

    fun getAmount(): Long {
        return if (isEmpty) 0L else amount
    }

    fun setAmount(amount: Long) {
        check(rawFluid != Fluids.EMPTY) { "Can't modify the empty stack." }
        this.amount = amount
        updateEmpty()
    }

    fun grow(amount: Long) {
        setAmount(this.amount + amount)
    }

    fun shrink(amount: Long) {
        setAmount(this.amount - amount)
    }

    fun isEmpty(): Boolean {
        return isEmpty
    }

    protected fun updateEmpty() {
        isEmpty = rawFluid == Fluids.EMPTY || amount <= 0L
    }

    fun hasTag(): Boolean {
        return tag != null
    }

    fun getTag(): CompoundTag? {
        return tag
    }

    fun setTag(tag: CompoundTag?) {
        check(rawFluid != Fluids.EMPTY) { "Can't modify the empty stack." }
        this.tag = tag
    }

    fun getOrCreateTag(): CompoundTag {
        if (tag == null) {
            setTag(CompoundTag())
        }
        return tag!!
    }

    fun getChildTag(childName: String): CompoundTag? {
        if (tag == null) {
            return null
        }
        return tag!!.getCompound(childName)
    }

    fun getOrCreateChildTag(childName: String): CompoundTag {
        val rootTag = getOrCreateTag()
        val child = rootTag.getCompound(childName)
        if (!rootTag.contains(childName, Tag.TAG_COMPOUND.toInt())) {
            rootTag.put(childName, child)
        }
        return child
    }

    fun removeChildTag(childName: String) {
        if (tag != null) {
            tag!!.remove(childName)
        }
    }

    fun copy(): LongFluidStack {
        return LongFluidStack(fluid, amount, tag?.copy())
    }

    fun toFluidStack(): FluidStack {
        if (isEmpty()) return FluidStack.EMPTY
        return FluidStack(fluid, amount.roundInt(), tag?.copy())
    }

    fun writeToPacket(buf: FriendlyByteBuf) {
        buf.writeRegistryId(ForgeRegistries.FLUIDS, fluid)
        buf.writeVarLong(amount)
        buf.writeNbt(tag)
    }

    fun writeToNBT(nbt: CompoundTag): CompoundTag {
        nbt.putString(KEY_FLUID_NAME, ForgeRegistries.FLUIDS.getKey(fluid).toString())
        nbt.putLong(KEY_AMOUNT, amount)

        val stackTag = tag
        if (stackTag != null) {
            nbt.put(KEY_TAG, stackTag)
        }
        return nbt
    }

    fun isFluidEqual(other: LongFluidStack): Boolean {
        return fluid == other.fluid && isFluidStackTagEqual(other)
    }

    fun isFluidEqual(other: FluidStack): Boolean {
        return fluid == other.fluid && areTagsEqual(tag, other.tag)
    }

    private fun isFluidStackTagEqual(other: LongFluidStack): Boolean {
        val selfTag = tag
        return selfTag == null && other.tag == null || (selfTag != null && other.tag != null && selfTag == other.tag)
    }

    fun containsFluid(other: LongFluidStack): Boolean {
        return isFluidEqual(other) && amount >= other.amount
    }

    fun isFluidStackIdentical(other: LongFluidStack): Boolean {
        return isFluidEqual(other) && amount == other.amount
    }

    fun isFluidEqual(other: ItemStack): Boolean {
        return FluidUtil.getFluidContained(other).map { stack -> isFluidEqual(stack) }.orElse(false)!!
    }

    override fun hashCode(): Int {
        var code = 1
        code = 31 * code + fluid.hashCode()
        if (tag != null) {
            code = 31 * code + tag.hashCode()
        }
        return code
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LongFluidStack) {
            return false
        }
        return isFluidEqual(other)
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
        fun readFromPacket(buf: FriendlyByteBuf): LongFluidStack {
            val fluid = buf.readRegistryId<Fluid>()
            val amount = buf.readVarLong()
            val tag = buf.readNbt()
            if (fluid == null || fluid == Fluids.EMPTY) return EMPTY
            return of(fluid, amount, tag)
        }

        @JvmStatic
        fun loadFluidStackFromNBT(tag: CompoundTag?): LongFluidStack {
            if (tag == null) return EMPTY
            if (!tag.contains(KEY_FLUID_NAME, Tag.TAG_STRING.toInt())) return EMPTY

            val fluidId = ResourceLocation.tryParse(tag.getString(KEY_FLUID_NAME)) ?: return EMPTY.copy()
            val fluid = ForgeRegistries.FLUIDS.getValue(fluidId) ?: Fluids.EMPTY
            if (fluid == Fluids.EMPTY) return EMPTY.copy()

            val amount = if (tag.contains(KEY_AMOUNT, Tag.TAG_LONG.toInt())) {
                tag.getLong(KEY_AMOUNT)
            } else {
                tag.getInt(KEY_AMOUNT).toLong()
            }
            if (amount <= 0L) return EMPTY.copy()

            val copiedTag = if (tag.contains(KEY_TAG, Tag.TAG_COMPOUND.toInt())) {
                tag.getCompound(KEY_TAG).copy()
            } else {
                null
            }

            return LongFluidStack(fluid, amount, copiedTag)
        }

        @JvmStatic
        fun areFluidStackTagsEqual(first: LongFluidStack, second: LongFluidStack): Boolean {
            return first.isFluidStackTagEqual(second)
        }

        @JvmStatic
        fun areTagsEqual(first: CompoundTag?, second: CompoundTag?): Boolean {
            if (first == null && second == null) return true
            if (first == null || second == null) return false
            return first == second
        }
    }
}

fun FluidStack.toLongFluidStack(): LongFluidStack {
    return LongFluidStack(fluid, amount.toLong(), tag?.copy())
}