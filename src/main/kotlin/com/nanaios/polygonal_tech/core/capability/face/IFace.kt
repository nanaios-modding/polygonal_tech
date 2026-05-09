package com.nanaios.polygonal_tech.core.capability.face

import net.minecraft.resources.ResourceLocation

/**
 * 外部のコンポーネントやパイプとやり取りする際の「面（Face）」の概念を抽象化し、
 * 単なる物理的方角（Up, Downなど）に留まらない柔軟なアクセスの定義を目的とするインターフェース。
 */
interface IFace {
    /** 該当する面の一意な識別子（[ResourceLocation]） */
    val id: ResourceLocation
}