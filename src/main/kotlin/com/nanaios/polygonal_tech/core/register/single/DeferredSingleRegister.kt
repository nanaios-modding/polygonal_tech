package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import com.nanaios.polygonal_tech.core.register.cast
import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry

abstract class DeferredSingleRegister<T> (
    protected val register: DeferredRegister<T>,
    override val modId: String
) : IDeferredSingleRegister<T>, IDeferredRegister<T> by register.cast() {
    constructor(registry: IForgeRegistry<T>, modId: String) : this(
        DeferredRegister.create(registry, modId),
        modId
    )

    constructor(registry: ResourceKey<out Registry<T>>, modId: String) : this(
        DeferredRegister.create(registry, modId),
        modId
    )

    constructor(registryName: ResourceLocation, modId: String) : this(
        DeferredRegister.create(registryName, modId),
        modId
    )

    override val cast: DeferredRegister<T>
        get() = register

    override fun <I : T> register(name: String, sup: (ResourceLocation) -> I): IRegistryObject<I> = register.cast().register(name, sup)
}