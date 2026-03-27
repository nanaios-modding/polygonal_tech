package com.nanaios.polygonal_tech.main.capability.item;

import com.nanaios.polygonal_tech.main.capability.base.BaseCombinedCapability;
import com.nanaios.polygonal_tech.main.capability.interfaces.IItemSlot;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCombinedCapability extends BaseCombinedCapability<IItemSlot> implements IItemHandlerModifiable {
    public ItemCombinedCapability(@Nullable Direction side, List<IItemSlot> capabilities) {
        super(side, capabilities);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        IItemSlot itemSlot = capabilities.get(slot);
        if(!itemSlot.canInput(side)) return;
        itemSlot.setStack(stack);
    }

    @Override
    public int getSlots() {
        return capabilities.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return capabilities.get(slot).getStack();
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        IItemSlot itemSlot = capabilities.get(slot);
        if(!itemSlot.canInput(side)) return stack;
        return itemSlot.insertItem(stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        IItemSlot itemSlot = capabilities.get(slot);
        if(!itemSlot.canOutput(side)) return ItemStack.EMPTY;
        return itemSlot.extractItem(amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return capabilities.get(slot).getSlotLimit();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return capabilities.get(slot).isItemValid(stack);
    }
}
