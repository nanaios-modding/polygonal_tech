package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.util.RGBA
import net.minecraft.network.chat.MutableComponent

interface ICapabilityInfo {
    val mode:IOMode
    val translation: MutableComponent
    val color: RGBA
}