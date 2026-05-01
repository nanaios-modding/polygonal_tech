package com.nanaios.polygonal_tech.core.network.sync.type

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

enum class SyncType(modId: String, name: String) : ISyncType {
    NONE(PolygonalTech.MOD_ID, "none"),
    GUI_OPENED(PolygonalTech.MOD_ID, "gui_opened"),
    ALWAYS(PolygonalTech.MOD_ID, "always");

    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(modId, "sync/$name")
}