package com.nanaios.polygonal_tech.core.capability.io

import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

interface IIOMode {
    val id: ResourceLocation
    val translation: MutableComponent
}