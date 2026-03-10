package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.impl.EmptyItemSlot;
import com.nanaios.polygonal_tech.capability.impl.ItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemContainer extends BaseContainer<IItemSlot> implements IItemSlot{
    public static String STORED_ITEM_KEY = "stored";

    public ItemContainer(IItemSlot base, IItemSlot input, IItemSlot output) {
        super(base, input, output, EmptyItemSlot.INSTANCE);
    }

    public static ItemContainer create(Predicate<ItemStack> validator) {
        ItemSlot base = new ItemSlot(validator);
        return new ItemContainer(
                base,
                new ItemSlot.InputOnly(base),
                new ItemSlot.OutputOnly(base)
        );
    }

    @Override
    public @NotNull ItemStack getStack() {
        return base.getStack();
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        return base.insertItem(stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int amount, boolean simulate) {
        return base.extractItem(amount, simulate);
    }

    @Override
    public int getSlotLimit() {
        return base.getSlotLimit();
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return base.isItemValid(stack);
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(STORED_ITEM_KEY, base.getStack().save(new CompoundTag()));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(STORED_ITEM_KEY)) {
            ItemStack stack = ItemStack.of(nbt.getCompound(STORED_ITEM_KEY));
            base.insertItem(stack, false);
        }
    }
}
