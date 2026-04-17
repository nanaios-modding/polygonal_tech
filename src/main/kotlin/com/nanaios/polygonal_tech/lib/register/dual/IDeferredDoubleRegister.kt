package com.nanaios.polygonal_tech.lib.register.dual

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.RegistryObject

/**
 * 2つのIDeferredSingleRegisterをまとめるためのinterface。
 * 例えば、ItemとBlockのIDeferredSingleRegisterをまとめるために使用できます。
 * */
interface IDeferredDoubleRegister<T1,T2>: IDeferredRegister<T1> {
    /**
     * 2つ目の[net.minecraftforge.registries.DeferredRegister.getEntries]のラップメソッド。
     * */
    fun getSecondEntries(): MutableCollection<RegistryObject<T2>>
    /**
     * 2つ目の[net.minecraftforge.registries.DeferredRegister.getRegistryKey]のラップメソッド。
     * */
    fun getSecondRegistryKey(): ResourceKey<out Registry<T2>>
    /**
     * 2つ目の[net.minecraftforge.registries.DeferredRegister.getRegistryName]のラップメソッド。
     * */
    fun getSecondRegistryName(): ResourceLocation
}