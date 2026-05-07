package com.nanaios.polygonal_tech.core.capability.io

import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

/**
 * 外部のパイプ・導管等とのやり取りにおいて、「搬入のみ」「搬出のみ」「入出力可能」などの
 * 入出力モード（I/Oモード）の特性を一元定義し表現することを目的としたインターフェース。
 */
interface IIOMode {
    /** このモードの固有識別子 */
    val id: ResourceLocation
    /** このモードの画面表示用テキストコンポーネント */
    val translation: MutableComponent
}