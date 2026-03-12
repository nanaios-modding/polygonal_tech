package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import com.nanaios.polygonal_tech.util.interfaces.IEventType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

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
    public int getMenuX() {
        return 0;
    }

    @Override
    public int getMenuY() {
        return 0;
    }

    @Override
    public boolean canInput() {
        return false;
    }

    @Override
    public boolean canOutput() {
        return false;
    }
}
