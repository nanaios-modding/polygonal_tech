package com.nanaios.polygonal_tech.core.register.single

import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTab

/**
 * MOD専用のクリエイティブタブ（[CreativeModeTab]）の登録を管理し、特定のIDから検索するための機能を提供することを目的としたクラス。
 *
 * @param modId 属するMODのID
 */
class DeferredSingleCreativeTabRegister(modId: String): DeferredSingleRegister<CreativeModeTab>(
    Registries.CREATIVE_MODE_TAB, modId
)