package com.nanaios.polygonal_tech.capability.provider;

import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.container.impl.CombinedItemContainer;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerProvider extends BaseProvider<IItemHandler, CombinedItemContainer> {
    public static String NBT_KEY = "item_handler";
    public ItemHandlerProvider() {
        super(CombinedItemContainer::new);
    }
}
