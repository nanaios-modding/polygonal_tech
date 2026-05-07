package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

/**
 * どの面にも属さない、または無効なアクセスであることを表現するためのNullオブジェクトパターンを目的とした[IFace]の実装。
 */
object EmptyFace: IFace {
    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "face/empty")

}