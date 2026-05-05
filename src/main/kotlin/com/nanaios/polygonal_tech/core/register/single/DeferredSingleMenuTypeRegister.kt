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

    /**
     * MenuTypeを登録するメソッド
     * @param name 登録名
     * @param sup MenuTypeを生成するためのサプライヤー。ResourceLocation, MenuType, windowId, Inventory, FriendlyByteBufを受け取り、AbstractContainerMenuを返す。
     * @return 登録されたMenuTypeのIRegistryObject
     */
    fun <M: AbstractContainerMenu> register(name: String, sup: (ResourceLocation, MenuType<M>, Int, Inventory, FriendlyByteBuf) -> M): IRegistryObject<MenuType<M>> {
        val registryObject = super.register(name) { location ->
            // IForgeMenuType.createを使用してMenuTypeを作成する。
            // 実際のメニュー生成（Factory.create）はメニューが開かれる時に行われるため、その時点ではget()を安全に呼び出せる。
            IForgeMenuType.create { windowId, inventory, buf ->
                @Suppress("UNCHECKED_CAST")
                val menuType = MAP[location]!!.get() as MenuType<M>
                sup(location, menuType, windowId, inventory, buf)
            }
        }
        return registryObject
    }
}