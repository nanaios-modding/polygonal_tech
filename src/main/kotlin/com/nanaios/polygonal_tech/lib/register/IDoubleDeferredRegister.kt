package com.nanaios.polygonal_tech.lib.register

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.RegistryObject

/**
 * 2つのDeferredRegisterをまとめるためのinterface。
 * 例えば、ItemとBlockのDeferredRegisterをまとめるために使用できます。
 * */
interface IDoubleDeferredRegister<T1,T2>: IDeferredRegister<T1> {
    fun getSecondEntries(): MutableCollection<RegistryObject<T2>>
    fun getSecondRegistryKey(): ResourceKey<out Registry<T2>>
    fun getSecondRegistryName(): ResourceLocation
}