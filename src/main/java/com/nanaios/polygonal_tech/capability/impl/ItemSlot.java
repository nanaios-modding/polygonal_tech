package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemSlot implements IItemSlot {
    protected final Predicate<ItemStack> validator;
    protected ItemStack stack = ItemStack.EMPTY;
    protected int x, y;

    public ItemSlot(Predicate<ItemStack> validator,int x, int y) {
        this.validator = validator;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public @NotNull ItemStack getStack() {
        return stack;
    }

    @Override
    public void setStack(@NotNull ItemStack stack) {
        if (isItemValid(stack)) {
            this.stack = stack;
        }
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        if (!isItemValid(stack)) return stack;
        if (this.stack.isEmpty()) {
            if (!simulate) {
                this.stack = stack.copy();
            }
            return ItemStack.EMPTY;
        }

        if (ItemStack.isSameItemSameTags(this.stack, stack)) {
            int space = getSlotLimit() - this.stack.getCount();
            int toInsert = Math.min(space, stack.getCount());
            if (!simulate) {
                this.stack.grow(toInsert);
            }
            ItemStack remainder = stack.copy();
            remainder.shrink(toInsert);
            return remainder;
        }
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int amount, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        int toExtract = Math.min(amount, stack.getCount());
        ItemStack extracted = stack.copy();
        extracted.setCount(toExtract);
        if (!simulate) {
            stack.shrink(toExtract);
        }
        return extracted;
    }

    @Override
    public int getSlotLimit() {
        return stack.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return validator.test(stack);
    }
}
