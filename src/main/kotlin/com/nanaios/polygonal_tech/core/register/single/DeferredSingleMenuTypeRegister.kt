package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.ForgeRegistries

class DeferredSingleMenuTypeRegister(modId: String): DeferredSingleRegister<MenuType<*>>(
    ForgeRegistries.MENU_TYPES,modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out MenuType<*>>> = mutableMapOf()

        fun getMenuTypeRegistryObject(location: ResourceLocation): IRegistryObject<out MenuType<*>>? {
            return MAP[location]
        }
    }

    fun <I : MenuType<*>> register(name: String, sup: (ResourceLocation, MenuType<*>) -> I): IRegistryObject<I> {
        return super.register(
            name
        ) { location -> sup(location, MAP[location]!!.get()) }
    }

    fun <M: AbstractContainerMenu> register(name: String, sup: (ResourceLocation, MenuType<*>, Int, Inventory, FriendlyByteBuf) -> M): IRegistryObject<MenuType<M>> {
        return this.register(
            name
        ) { location,menuType -> IForgeMenuType.create{windowId, inventory, buf -> sup(location, menuType,windowId,inventory,buf) }  }
    }
}