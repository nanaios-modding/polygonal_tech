package com.nanaios.polygonal_tech.lib.register

import com.nanaios.polygonal_tech.lib.util.IModIdProvider
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

interface IDeferredSingleRegister<T> : IDeferredRegister<T>, IModIdProvider {
    /**
     * IDeferredSingleRegisterをDeferredRegisterにキャストして返します。
     * */
    fun cast():DeferredRegister<T>
}

/**
 * 基本的なDeferredSingleRegisterの実装クラスです。
 * DeferredRegisterを内部に持ち、登録されたオブジェクトのResourceLocationとRegistryObjectのマッピングを管理します。
 * 特定のタイプのオブジェクト（例:アイテム、ブロックなど）に特化した登録クラスを作成するためにこのクラスを継承してください。
 * */
abstract class DeferredSingleRegister<T> private constructor(
    protected val register: DeferredRegister<T>,
    override val modId: String,
    protected val map: MutableMap<ResourceLocation, RegistryObject<out T>>
): IDeferredSingleRegister<T>,IDeferredRegister<T> by register.cast() {
    constructor(registry: IForgeRegistry<T>,modId: String,map: MutableMap<ResourceLocation, RegistryObject<out T>>)
            :this(DeferredRegister.create(registry,modId),modId,map)

    override fun <I : T> register(name: String, sup: Supplier<out I>): RegistryObject<I> {
        val registryObject: RegistryObject<I> = register.register(name, sup)
        map[registryObject.id] = registryObject
        return registryObject
    }

    override fun cast(): DeferredRegister<T> {
        return register
    }
}