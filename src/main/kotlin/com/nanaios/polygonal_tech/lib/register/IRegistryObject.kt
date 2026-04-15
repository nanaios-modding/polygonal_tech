package com.nanaios.polygonal_tech.lib.register

import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.RegistryObject
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier
import java.util.stream.Stream

/**
 * [RegistryObject]をラップするためのinterface。
 * RegistryObjectにはpublicなconstructorが存在せず、通常の方法での継承が使用できません
 * そこで、このinterfaceをRegistryObjectにmixinして実装します。
 * そうすることで、RegistryObjectのメソッドをIRegistryObjectのメソッドとして使用できるようになります。
 * これにより、移譲を用いてRegistryObjectの機能を少ない記述量で継承できます
 * */
interface IRegistryObject<T>: Supplier<T> {
    fun getId(): ResourceLocation

    fun getKey(): ResourceKey<T>

    fun stream(): Stream<T>

    fun isPresent(): Boolean

    fun ifPresent(consumer: Consumer<in T>)

    fun filter(predicate: Predicate<in T>): RegistryObject<T>

    fun <U> map(mapper: Function<in T, out U>): Optional<U>

    fun <U> flatMap(mapper: Function<in T, Optional<U>>): Optional<U>

    fun <U> lazyMap(mapper: Function<in T, out U>): Supplier<U>

    fun orElse(other: T): T

    fun orElseGet(other: Supplier<out T>): T

    fun <X : Throwable> orElseThrow(exceptionSupplier: Supplier<out X>): T

    fun getHolder(): Optional<Holder<T>>
}