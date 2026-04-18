package com.nanaios.polygonal_tech.lib.register.dual

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegistryObject

/**
 * 基本的ななIDeferredDualRegisterの実装クラスです。
 * 2つのIDeferredSingleRegisterを持ちます。
 * 特定のタイプのオブジェクト（例:アイテム、ブロックなど）に特化した登録クラスを作成するためにこのクラスを継承してください。
 * */
abstract class DeferredDoubleRegister<T1, T2>(
    protected val firstRegister: IDeferredRegister<T1>,
    protected val secondRegister: IDeferredRegister<T2>
) : IDeferredDoubleRegister<T1, T2>, IDeferredRegister<T1> by firstRegister {
    init {
        if (firstRegister.modId != secondRegister.modId) {
            throw IllegalArgumentException("Both registers must have the same mod ID.")
        }
    }

    override val secondEntries: MutableCollection<RegistryObject<T2>>
        get() = secondRegister.entries
    override val secondRegistryKey: ResourceKey<out Registry<T2>>
        get() = secondRegister.registryKey
    override val secondRegistryName: ResourceLocation
        get() = secondRegister.registryName

    override fun register(bus: IEventBus) {
        firstRegister.register(bus)
        secondRegister.register(bus)
    }
}