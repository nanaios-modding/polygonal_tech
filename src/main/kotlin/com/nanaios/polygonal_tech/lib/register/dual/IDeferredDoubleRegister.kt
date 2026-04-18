package com.nanaios.polygonal_tech.lib.register.dual

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.RegistryObject
import net.minecraftforge.registries.DeferredRegister

/**
 * 2つのIDeferredSingleRegisterをまとめるためのinterface。
 * 例えば、ItemとBlockのIDeferredSingleRegisterをまとめるために使用できます。
 * */
interface IDeferredDoubleRegister<T1,T2>: IDeferredRegister<T1> {
    /**
     * 2つ目の[DeferredRegister.getEntries]のラッププロパティ。
     * */
    val secondEntries: MutableCollection<RegistryObject<T2>>
    /**
     * 2つ目の[DeferredRegister.getRegistryKey]のラッププロパティ。
     * */
    val secondRegistryKey: ResourceKey<out Registry<T2>>
    /**
     * 2つ目の[DeferredRegister.getRegistryName]のラッププロパティ。
     * */
    val secondRegistryName: ResourceLocation
}