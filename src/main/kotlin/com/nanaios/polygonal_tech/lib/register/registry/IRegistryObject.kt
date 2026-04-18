package com.nanaios.polygonal_tech.lib.register.registry

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
 * [net.minecraftforge.registries.RegistryObject]をラップするためのinterface。
 * RegistryObjectにはpublicなconstructorが存在せず、通常の方法での継承が使用できません
 * そこで、このinterfaceをRegistryObjectにmixinして実装します。
 * そうすることで、RegistryObjectのメソッドをIRegistryObjectのメソッドとして使用できるようになります。
 * これにより、移譲を用いてRegistryObjectの機能を少ない記述量で継承できます
 * */
interface IRegistryObject<T>: Supplier<T> {
    /**
     * [net.minecraftforge.registries.RegistryObject.getId]のラッププロパティ。
     * */
    val id: ResourceLocation
    /**
     * [net.minecraftforge.registries.RegistryObject.getKey]のラッププロパティ。
     * */
    val key: ResourceKey<T>
    /**
     * [net.minecraftforge.registries.RegistryObject.getHolder]のラッププロパティ。
     * */
    val holder: Optional<Holder<T>>
    /**
     * [net.minecraftforge.registries.RegistryObject.stream]のラップメソッド。
     * */
    fun stream(): Stream<T>
    /**
     * [net.minecraftforge.registries.RegistryObject.ifPresent]のラップメソッド。
     * */
    fun isPresent(): Boolean
    /**
     * [net.minecraftforge.registries.RegistryObject.ifPresent]のラップメソッド。
     * */
    fun ifPresent(consumer: Consumer<in T>)
    /**
     * [net.minecraftforge.registries.RegistryObject.filter]のラップメソッド。
     * */
    fun filter(predicate: Predicate<in T>): RegistryObject<T>
    /**
     * [RegistryObject.map]のラップメソッド。
     * */
    fun <U> map(mapper: Function<in T, out U>): Optional<U>
    /**
     * [RegistryObject.flatMap]のラップメソッド。
     * */
    fun <U> flatMap(mapper: Function<in T, Optional<U>>): Optional<U>
    /**
     * [RegistryObject.lazyMap]のラップメソッド。
     * */
    fun <U> lazyMap(mapper: Function<in T, out U>): Supplier<U>
    /**
     * [RegistryObject.orElse]のラップメソッド。
     * */
    fun orElse(other: T): T
    /**
     * [RegistryObject.orElseGet]のラップメソッド。
     * */
    fun orElseGet(other: Supplier<out T>): T
    /**
     * [RegistryObject.orElseThrow]のラップメソッド。
     * */
    fun <X : Throwable> orElseThrow(exceptionSupplier: Supplier<out X>): T
}


/**
 * [RegistryObject]を[IRegistryObject]にキャストするための拡張関数
 * */
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <T> RegistryObject<T>.cast(): IRegistryObject<T> {
    // エディタ上ではRegistryObjectはfinal classのためIRegistryObjectへのキャストは常に失敗すると表示される
    // しかし、実際にはRegistryObjectはmixinによってIRegistryObjectを実装しているため、キャストは成功する
    return this as IRegistryObject<T>
}