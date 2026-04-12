package com.nanaios.polygonal_tech.lib.register

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

/**
 * [DeferredRegister]をラップするためのinterface。
 * DeferredRegisterにはpublicなconstructorが存在せず、通常の方法での継承が使用できません
 * そこで、このinterfaceをDeferredRegisterにmixinして実装します。
 * そうすることで、DeferredRegisterのメソッドをIDeferredRegisterのメソッドとして使用できるようになります。
 * これにより、移譲を用いてDeferredRegisterの機能を少ない記述量で継承できます
 * */
interface IDeferredRegister<T> {
    fun <I : T> register(name: String, sup: Supplier<out I>): RegistryObject<I>
    fun register(bus: IEventBus)
    fun getEntries(): MutableCollection<RegistryObject<T>>
    fun getRegistryKey(): ResourceKey<out Registry<T>>
    fun getRegistryName(): ResourceLocation
}

/**
 * [DeferredRegister]を[IDeferredRegister]にキャストするための拡張関数
 * */
@Suppress("UNCHECKED_CAST")
fun <T>DeferredRegister<T>.cast():IDeferredRegister<T> {
    // mixinによってDeferredRegisterはIDeferredRegisterを実装するため、キャストして返すことができます。
    return this as IDeferredRegister<T>
}
