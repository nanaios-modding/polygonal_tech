package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab

/**
 * MOD専用のクリエイティブタブ（[CreativeModeTab]）の登録を管理し、特定のIDから検索するための機能を提供することを目的としたクラス。
 *
 * @param modId 属するMODのID
 */
class DeferredSingleCreativeTabRegister(modId: String): DeferredSingleRegister<CreativeModeTab>(
    Registries.CREATIVE_MODE_TAB, modId, MAP
) {
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out CreativeModeTab>> = mutableMapOf()

        /**
         * [ResourceLocation]から、登録された[CreativeModeTab]の情報を逆引き取得することを目的とするメソッド。
         *
         * @param location 対象タブの[ResourceLocation]
         * @return 登録済みの[CreativeModeTab]を保持する[IRegistryObject]。未登録の場合は[null]。
         */
        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out CreativeModeTab>? {
            return MAP[location]
        }
    }
}