package com.nanaios.polygonal_tech.lib.util.sync.value

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

/**
 * ISyncValueに付与されるタイプを表すインターフェース。
 * ISyncValueがどのような同期タイプなのかを判別するために使用されます。
 * */
interface ISyncType {
    fun getId(): ResourceLocation
}

enum class SyncType(private val id: ResourceLocation): ISyncType {
    IN_GUI(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "in_gui")),
    ALWAYS(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "always"));

    override fun getId(): ResourceLocation = id
}