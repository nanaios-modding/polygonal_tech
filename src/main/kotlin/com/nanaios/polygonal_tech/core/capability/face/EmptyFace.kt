package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

/**
 * どの面にも属さない、または無効なアクセスであることを表現するためのNullオブジェクトパターンを目的とした[IFace]の実装。
 */
object EmptyFace: IFace {
    override val translation: Component
        get() = Component.translatable("face.${PolygonalTech.MOD_ID}.empty")
}