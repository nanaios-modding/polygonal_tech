package com.nanaios.polygonal_tech.main.menu.base;

import com.nanaios.polygonal_tech.main.capability.interfaces.IItemSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ItemSlotHandler extends SlotItemHandler {
    private final IItemSlot slot;
    public ItemSlotHandler(IItemSlot slot,int index) {
        super(slot, index, slot.getMenuX(), slot.getMenuY());
        this.slot = slot;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return slot.canOutput(null);
    }
}
