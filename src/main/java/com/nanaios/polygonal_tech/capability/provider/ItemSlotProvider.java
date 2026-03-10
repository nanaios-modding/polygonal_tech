package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.container.impl.CombinedItemContainer;

public class ItemSlotProvider extends BaseProvider<IItemSlot, CombinedItemContainer> {
    public static String NBT_KEY = "item_handler";
    public ItemSlotProvider() {
        super(CombinedItemContainer::new);
    }
}
