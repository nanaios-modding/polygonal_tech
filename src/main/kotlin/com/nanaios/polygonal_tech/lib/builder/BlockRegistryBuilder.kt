package com.nanaios.polygonal_tech.lib.builder

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

open class BlockRegistryBuilder<T : Block>(protected val builder: (location: ResourceLocation) -> T) : Supplier<T> {
    protected lateinit var location: ResourceLocation

    fun setResourceLocation(location: ResourceLocation): BlockRegistryBuilder<T> {
        this.location = location
        return this
    }

    override fun get(): T {
        return builder(location)
    }
}