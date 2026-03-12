package com.nanaios.polygonal_tech.capability;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.event.PolygonalTechEventType;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import com.nanaios.polygonal_tech.util.interfaces.IEventType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ItemSlot implements IItemSlot {
    protected final List<Consumer<CapabilityUpdateEvent>> listeners = new ArrayList<>();
    protected final Predicate<ItemStack> validator;
    protected ItemStack stack = ItemStack.EMPTY;
    protected int x, y;

    public ItemSlot(Predicate<ItemStack> validator,int x, int y) {
        this.validator = validator;
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
        if(amount <= 0) return ItemStack.EMPTY;
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

    @Override
    public boolean canInput() {
        return stack.isEmpty();
    }

    @Override
    public boolean canOutput() {
        return !stack.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends IEvent> boolean addListener(IEventType<E> type, Consumer<E> listener) {
        if(type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return listeners.add((Consumer<CapabilityUpdateEvent>) listener);
        };
        return false;
    }

    @Override
    public <E extends IEvent> boolean removeListener(IEventType<E> type, Consumer<E> listener) {
        if(type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストに追加します。
            return listeners.remove(listener);
        };
        return false;
    }

    @Override
    public <E extends IEvent> void triggerEvent(IEventType<E> type, E event) {
        if(type == PolygonalTechEventType.CAPABILITY_UPDATE) {
            // この時点でEはCapabilityUpdateEventであることが保証されているため、キャストしてリストのリスナーにイベントを通知します。
            for (Consumer<CapabilityUpdateEvent> listener : listeners) {
                listener.accept((CapabilityUpdateEvent) event);
            }
        }
    }
}
