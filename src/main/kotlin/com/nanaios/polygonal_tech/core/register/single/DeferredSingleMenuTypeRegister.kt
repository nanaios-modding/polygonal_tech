package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.registries.ForgeRegistries

class DeferredSingleMenuTypeRegister(modId: String): DeferredSingleRegister<MenuType<*>>(
    ForgeRegistries.MENU_TYPES,modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out MenuType<*>>> = mutableMapOf()

        fun getItemRegistryObject(location: ResourceLocation): IRegistryObject<out MenuType<*>>? {
            return MAP[location]
        }
    }
}