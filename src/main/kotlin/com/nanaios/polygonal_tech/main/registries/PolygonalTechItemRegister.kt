package com.nanaios.polygonal_tech.main.registries

import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleItemRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.item.Item

object PolygonalTechItemRegister {
    val TEST_ITEMS = DeferredSingleItemRegister(PolygonalTech.MOD_ID)

    init {
        TEST_ITEMS.register("test_1") {
            Item(Item.Properties())
        }
    }
}