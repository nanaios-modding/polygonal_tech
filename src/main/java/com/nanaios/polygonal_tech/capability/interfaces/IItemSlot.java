package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public interface IItemSlot extends IItemHandlerModifiable ,IHasIOStatus{
    @NotNull ItemStack getStack();
    void setStack(@NotNull ItemStack stack);
    @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate);
    @NotNull ItemStack extractItem(int amount, boolean simulate);
    int getSlotLimit();
    boolean isItemValid(@NotNull ItemStack stack);
    int getX();
    int getY();

    default int getSlots() {
        return 1;
    }
    default @NotNull ItemStack getStackInSlot(int slot) {
        return slot == 0 ? getStack() : ItemStack.EMPTY;
    }

    default void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot == 0) setStack(stack);
    }

    default @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return slot == 0 ? insertItem(stack, simulate) : stack;
    }
    default @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot == 0 ? extractItem(amount, simulate) : ItemStack.EMPTY;
    }
    default int getSlotLimit(int slot) {
        return slot == 0 ? getSlotLimit() : 0;
    };
    default boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return slot == 0 && isItemValid(stack);
    }
}
