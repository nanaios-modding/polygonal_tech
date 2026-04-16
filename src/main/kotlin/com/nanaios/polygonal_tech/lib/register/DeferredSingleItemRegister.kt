package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

open class DeferredSingleItemRegister(modId: String): DeferredSingleRegister<Item>(
    ForgeRegistries.ITEMS,modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out Item>> = mutableMapOf()

        fun getItemRegistryObject(location: ResourceLocation): IRegistryObject<out Item>? {
            return MAP[location]
        }
    }
}