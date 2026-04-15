package com.nanaios.polygonal_tech.lib.register

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegistryObject

abstract class DeferredDoubleRegister<T1,T2> constructor(
    protected val firstRegister: IDeferredRegister<T1>,
    protected val secondRegister: IDeferredRegister<T2>
): IDeferredDoubleRegister<T1,T2>
{
    override fun getEntries(): MutableCollection<RegistryObject<T1>> {
        return firstRegister.getEntries()
    }

    override fun getRegistryKey(): ResourceKey<out Registry<T1>> {
        return firstRegister.getRegistryKey()
    }

    override fun getRegistryName(): ResourceLocation {
        return firstRegister.getRegistryName()
    }

    override fun getSecondEntries(): MutableCollection<RegistryObject<T2>> {
        return secondRegister.getEntries()
    }

    override fun getSecondRegistryKey(): ResourceKey<out Registry<T2>> {
        return secondRegister.getRegistryKey()
    }

    override fun getSecondRegistryName(): ResourceLocation {
        return secondRegister.getRegistryName()
    }

    override fun register(bus: IEventBus) {
        firstRegister.register(bus)
        secondRegister.register(bus)
    }
}