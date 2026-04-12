package com.nanaios.polygonal_tech.main.registries

import com.nanaios.polygonal_tech.lib.register.DeferredItemRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.item.Item

object PolygonalTechItemRegister {
    val TEST_ITEMS = DeferredItemRegister(PolygonalTech.MOD_ID)
    val ITEM_1 = TEST_ITEMS.register("") {
        Item(Item.Properties())
    }
}