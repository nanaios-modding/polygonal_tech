package com.nanaios.polygonal_tech.capability.item;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;

import java.util.function.Consumer;

public class ItemSlotProvider extends BaseProvider<IItemSlot, ItemCombinedCapability> {
    public static String NBT_ITEM_SLOTS = "item_slots";
    public ItemSlotProvider(Consumer<CapabilityUpdateEvent> capabilityUpdateListener) {
        super(ItemCombinedCapability::new, capabilityUpdateListener);
    }
}
