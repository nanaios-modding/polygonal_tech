package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

abstract class DeferredBaseRegister<T> private constructor(
    val register: DeferredRegister<T>,
    val modId: String,
    val map: MutableMap<ResourceLocation, RegistryObject<out T>>
): IDeferredRegister<T> by register.cast() {
    constructor(registry: IForgeRegistry<T>,modId: String,map: MutableMap<ResourceLocation, RegistryObject<out T>>)
            :this(DeferredRegister.create(registry,modId),modId,map)

    override fun <I : T> register(name: String, sup: Supplier<out I>): RegistryObject<I> {
        val registryObject: RegistryObject<I> = register.register(name, sup)
        map[registryObject.id] = registryObject
        return registryObject
    }

    fun getModId(): String {
        return modId
    }
}