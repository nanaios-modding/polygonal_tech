package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.base.BaseCapability;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemSlot extends BaseCapability implements IItemSlot {
    protected final Predicate<ItemStack> validator;
    protected ItemStack stack = ItemStack.EMPTY;
    protected int x, y;

    public ItemSlot(boolean arrowInput,boolean arrowOutput,Predicate<ItemStack> validator, int x, int y) {
        super(arrowInput, arrowOutput);
        this.validator = validator;
        this.x = x;
        this.y = y;
    }

    public int getMenuX() {
        return x;
    }

    public int getMenuY() {
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
            // 変更を通知
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
        }
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        if (!isItemValid(stack)) return stack;
        if (this.stack.isEmpty()) {
            if (!simulate) {
                this.stack = stack.copy();
                // 変更を通知
                triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
            }
            return ItemStack.EMPTY;
        }

        if (ItemStack.isSameItemSameTags(this.stack, stack)) {
            int space = getSlotLimit() - this.stack.getCount();
            int toInsert = Math.min(space, stack.getCount());
            if (!simulate) {
                this.stack.grow(toInsert);
                // 変更を通知
                triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
            }
            ItemStack remainder = stack.copy();
            remainder.shrink(toInsert);
            return remainder;
        }

        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;
        if (stack.isEmpty()) return ItemStack.EMPTY;
        int toExtract = Math.min(amount, stack.getCount());
        ItemStack extracted = stack.copy();
        extracted.setCount(toExtract);
        if (!simulate) {
            stack.shrink(toExtract);
            // 変更を通知
            triggerEvent(PolygonalTechEventType.CAPABILITY_UPDATE, CapabilityUpdateEvent.DEFAULT);
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
