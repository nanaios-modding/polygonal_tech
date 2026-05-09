package com.nanaios.polygonal_tech.core.capability

import net.minecraftforge.common.util.LazyOptional

data class CapabilityManager<C: Any>(val config:ICapabilityInfo, val capability: C) {
    var lazy : LazyOptional<C> = LazyOptional.empty()

    fun initCap() {
        lazy = LazyOptional.of { capability }
    }

    fun invalidate() {
        lazy.invalidate()
    }

    operator fun component3(): LazyOptional<C> = lazy
}
