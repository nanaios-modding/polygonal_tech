package com.nanaios.polygonal_tech.core.register.single

import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

/**
 * [Item]に特化した登録管理を行い、生成された全アイテムのID管理およびIDからの逆引き検索機能を提供することを目的とするクラス。
 *
 * @param modId 対象のMODの識別子
 */
open class DeferredSingleItemRegister(modId: String): DeferredSingleRegister<Item>(
    ForgeRegistries.ITEMS,modId
)