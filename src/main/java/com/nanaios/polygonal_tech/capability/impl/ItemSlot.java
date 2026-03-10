package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemSlot implements IItemSlot {
    protected final Predicate<ItemStack> validator;
    protected ItemStack stack = ItemStack.EMPTY;

    public ItemSlot(Predicate<ItemStack> validator) {
        this.validator = validator;
    }

    @Override
    public @NotNull ItemStack getStack() {
        return stack;
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

    public static class InputOnly extends ItemSlot {
        protected final IItemSlot base;
        public InputOnly(ItemSlot slot) {
            super(slot.validator);
            this.base = slot;
        }

        @Override
        public boolean isItemValid(@NotNull ItemStack stack) {
            return base.isItemValid(stack);
        }

        @Override
        public int getSlotLimit() {
            return base.getSlotLimit();
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
            return ItemStack.EMPTY;
        }
    }

    public static class OutputOnly extends ItemSlot {
        protected final IItemSlot base;
        public OutputOnly(ItemSlot slot) {
            super(slot.validator);
            this.base = slot;
        }

        @Override
        public boolean isItemValid(@NotNull ItemStack stack) {
            return false;
        }

        @Override
        public int getSlotLimit() {
            return base.getSlotLimit();
        }

        @Override
        public @NotNull ItemStack getStack() {
            return base.getStack();
        }

        @Override
        public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int amount, boolean simulate) {
            return base.extractItem(amount, simulate);
        }
    }
}
