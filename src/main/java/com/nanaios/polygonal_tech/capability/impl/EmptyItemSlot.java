package com.nanaios.polygonal_tech.capability.impl;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EmptyItemSlot implements IItemSlot {
    public static IItemSlot INSTANCE = new EmptyItemSlot();

    @Override
    public @NotNull ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(@NotNull ItemStack stack) {

    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit() {
        return 0;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
