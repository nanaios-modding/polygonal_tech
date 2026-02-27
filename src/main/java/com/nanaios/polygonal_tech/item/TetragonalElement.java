package com.nanaios.polygonal_tech.item;

import com.nanaios.polygonal_tech.item.interfaces.IPolygonalElementItem;
import net.minecraft.world.item.Item;

public class TetragonalElement extends Item implements IPolygonalElementItem {
    public TetragonalElement() {
        super(new Item.Properties());
    }

    @Override
    public int getCornerCount() {
        return 4;
    }
}
