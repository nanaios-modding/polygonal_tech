package com.nanaios.polygonal_tech.lib.register.single

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister
import com.nanaios.polygonal_tech.lib.register.IRegistryObject
import com.nanaios.polygonal_tech.lib.register.cast
import com.nanaios.polygonal_tech.lib.util.IModIdProvider
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry

/**
 * 基本的なIDeferredSingleRegisterの実装クラスです。
 * DeferredRegisterを内部に持ち、登録されたオブジェクトのResourceLocationとRegistryObjectのマッピングを管理します。
 * 特定のタイプのオブジェクト（例:アイテム、ブロックなど）に特化した登録クラスを作成するためにこのクラスを継承してください。
 * */
abstract class DeferredSingleRegister<T> private constructor(
    protected val register: DeferredRegister<T>,
    override val modId: String,
    protected val map: MutableMap<ResourceLocation, IRegistryObject<out T>>
): IDeferredSingleRegister<T>, IDeferredRegister<T> by register.cast() {
    constructor(registry: IForgeRegistry<T>, modId: String, map: MutableMap<ResourceLocation, IRegistryObject<out T>>)
            :this(DeferredRegister.create(registry,modId),modId,map)

    override fun <I : T> register(name: String, sup: (ResourceLocation) -> I): IRegistryObject<I> {
        val registry = register.cast().register(name,sup)
        map[registry.getId()] = registry
        return registry
    }

    override fun cast(): DeferredRegister<T> {
        return register
    }
}