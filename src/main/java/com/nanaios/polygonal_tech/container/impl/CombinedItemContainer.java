package com.nanaios.polygonal_tech.container.impl;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.container.base.CombinedContainer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombinedItemContainer extends CombinedContainer<IItemSlot> implements IItemHandler {
    public CombinedItemContainer(Direction side, List<BaseContainer<IItemSlot>> baseContainers) {
        super(side, baseContainers);
    }

    @Override
    public int getSlots() {
        return containers.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        BaseContainer<IItemSlot> container = containers.get(slot);
        IItemSlot itemSlot = container.getBase();
        return itemSlot.getStack();
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        BaseContainer<IItemSlot> container = containers.get(slot);
        IItemSlot itemSlot = container.getInput(side);
        if(itemSlot == null) return stack;
        return itemSlot.insertItem(stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        BaseContainer<IItemSlot> container = containers.get(slot);
        IItemSlot itemSlot = container.getOutput(side);
        if(itemSlot == null) return ItemStack.EMPTY;
        return itemSlot.extractItem(amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        BaseContainer<IItemSlot> container = containers.get(slot);
        IItemSlot itemSlot = container.getBase();
        return itemSlot.getSlotLimit();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        BaseContainer<IItemSlot> container = containers.get(slot);
        IItemSlot itemSlot = container.getInput(side);
        if(itemSlot == null) return false;
        return itemSlot.isItemValid(stack);
    }
}
