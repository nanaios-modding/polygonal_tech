package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.ForgeRegistries

/**
 * [MenuType]（GUIコンテナタイプ）の登録を専門に管理し、画面表示に必要なメニュータイプの一元化と検索機能を持たせることを目的とするクラス。
 *
 * @param modId 所属するMODのID
 */
class DeferredSingleMenuTypeRegister(modId: String): DeferredSingleRegister<MenuType<*>>(
    ForgeRegistries.MENU_TYPES,modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out MenuType<*>>> = mutableMapOf()

        /**
         * 特定のGUI要素を呼び出すために、登録された[MenuType]をIDをキーにして検索・取得することを目的としたメソッド。
         *
         * @param location メニュータイプの[ResourceLocation]
         * @return 対応する[MenuType]の[IRegistryObject]。存在しない場合は[null]。
         */
        fun getMenuTypeRegistryObject(location: ResourceLocation): IRegistryObject<out MenuType<*>>? {
            return MAP[location]
        }
    }

    /**
     * Forgeの拡張メニュータイプとして[MenuType]を安全かつ容易に登録し、初期化遅延を適切にハンドリングすることを目的としたメソッド。
     *
     * @param M メニューとなる[AbstractContainerMenu]の派生型
     * @param name 登録するメニュータイプの固有名称
     * @param sup クライアント側でメニューが開かれたときに、実際に[AbstractContainerMenu]のインスタンスを生成する目的で呼び出される関数。
     *            [ResourceLocation], [MenuType], windowId, [Inventory], [FriendlyByteBuf] を受け取る。
     * @return 登録された[MenuType]を内包する[IRegistryObject]
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