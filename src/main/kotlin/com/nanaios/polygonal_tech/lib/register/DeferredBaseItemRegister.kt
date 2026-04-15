package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

open class DeferredBaseItemRegister(modId: String, map: MutableMap<ResourceLocation, RegistryObject<out Item>>): DeferredBaseRegister<Item>(
    ForgeRegistries.ITEMS,modId, map
) {
    constructor(modId: String) : this(modId, MAP)
    companion object{
        private val MAP: MutableMap<ResourceLocation, RegistryObject<out Item>> = mutableMapOf()

        fun getItemRegistryObject(location: ResourceLocation): RegistryObject<out Item>? {
            return MAP[location]
        }
    }
}