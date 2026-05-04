package com.nanaios.polygonal_tech.core.translation

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

interface ITranslatable : Component {
    val static: MutableComponent
    fun translate(vararg args: Any): MutableComponent
}