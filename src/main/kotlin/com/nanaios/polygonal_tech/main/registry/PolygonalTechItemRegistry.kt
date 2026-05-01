package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.single.DeferredSingleItemRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.item.Item

object PolygonalTechItemRegistry {
    val TEST_ITEMS = DeferredSingleItemRegister(PolygonalTech.MOD_ID)

    init {
        TEST_ITEMS.register("test_1") {
            Item(Item.Properties())
        }
    }
}
