package com.nanaios.polygonal_tech.lib.register.single

import com.nanaios.polygonal_tech.lib.register.registry.IRegistryObject
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab

class DeferredSingleCreativeTabRegister(modId: String): DeferredSingleRegister<CreativeModeTab>(
    Registries.CREATIVE_MODE_TAB, modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out CreativeModeTab>> = mutableMapOf()

        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out CreativeModeTab>? {
            return MAP[location]
        }
    }
}