package com.nanaios.polygonal_tech.item;

import com.nanaios.polygonal_tech.item.interfaces.IElementItem;
import net.minecraft.world.item.Item;

public class ElementItem extends Item implements IElementItem {
    private final int cornerCount;

    public ElementItem(int cornerCount) {
        super(new Properties());
        this.cornerCount = cornerCount;
    }
    @Override
    public int getCornerCount() {
        return cornerCount;
    }
}
