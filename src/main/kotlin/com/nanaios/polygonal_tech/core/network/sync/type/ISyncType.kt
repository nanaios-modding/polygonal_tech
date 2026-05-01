package com.nanaios.polygonal_tech.core.network.sync.type

import net.minecraft.resources.ResourceLocation

/**
 * ISyncValueに付与されるタイプを表すインターフェース。
 * ISyncValueがどのような同期タイプなのかを判別するために使用されます。
 * */
interface ISyncType {
    val id: ResourceLocation
}