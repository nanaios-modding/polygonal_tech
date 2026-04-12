package com.nanaios.polygonal_tech.lib.register

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

interface IDeferredRegister<T> {
    fun <I : T> register(name: String, sup: Supplier<out I>): RegistryObject<I>
    fun getEntries(): MutableCollection<RegistryObject<T>>
    fun getRegistryKey(): ResourceKey<out Registry<T>>
    fun getRegistryName(): ResourceLocation
}
