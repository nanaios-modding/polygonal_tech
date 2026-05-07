package com.nanaios.polygonal_tech.core.network.sync.type

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

/**
 * [ISyncValue]の同期タイミングとなる[ISyncType]を実際の列挙として定義することを目的とする。
 * 用途に応じて[NONE]（同期不要）、[GUI_OPENED]（画面表示中限定）、[ALWAYS]（常時）を選択する。
 *
 * @param modId 処理を登録するMODのID
 * @param name 同期種別の名前
 */
enum class SyncType(modId: String, name: String) : ISyncType {
    NONE(PolygonalTech.MOD_ID, "none"),
    GUI_OPENED(PolygonalTech.MOD_ID, "gui_opened"),
    ALWAYS(PolygonalTech.MOD_ID, "always");

    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(modId, "sync/$name")
}