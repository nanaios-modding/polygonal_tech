package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import com.nanaios.polygonal_tech.core.register.cast
import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry

/**
 * [IDeferredSingleRegister]のデフォルトとなる実装クラス。
 * バックエンドとして[DeferredRegister]を保持し、生成された登録オブジェクト（[IRegistryObject]）と
 * その[ResourceLocation]の紐付けをマップで一元管理して再利用しやすくすることを目的としている。
 *
 * 特定の登録カテゴリ（ブロック、アイテム、タイルエンティティなど）ごとに、このクラスを継承した管理クラスを作成することが推奨される。
 *
 * @param T 管理対象となる各種登録要素の基底型
 * @property register 移譲先となる実際の[DeferredRegister]インスタンス
 * @property modId 対象のMODのID
 * @property map [ResourceLocation]と、それに対応する生成済み[IRegistryObject]を対応づけるマップ
 */
abstract class DeferredSingleRegister<T> private constructor(
    protected val register: DeferredRegister<T>,
    override val modId: String,
    protected val map: MutableMap<ResourceLocation, IRegistryObject<out T>>
): IDeferredSingleRegister<T>, IDeferredRegister<T> by register.cast() {
    constructor(registry: IForgeRegistry<T>, modId: String, map: MutableMap<ResourceLocation, IRegistryObject<out T>>)
            :this(DeferredRegister.create(registry,modId),modId,map)
    constructor(registry: ResourceKey<out Registry<T>>, modId: String, map: MutableMap<ResourceLocation, IRegistryObject<out T>>)
            :this(DeferredRegister.create(registry,modId),modId,map)

    override val cast: DeferredRegister<T>
        get() = register

    /**
     * 新しい要素を登録し、同時に内部の[map]へ登録オブジェクトをキャッシュすることを目的としたメソッド。
     *
     * @param I 実際に登録される要素の派生型
     * @param name 登録する識別名
     * @param sup レジストリに登録する要素のインスタンスを生成する関数。[ResourceLocation]が渡される。
     * @return 登録済みの要素へのアクセスを提供する[IRegistryObject]
     */
    override fun <I : T> register(name: String, sup: (ResourceLocation) -> I): IRegistryObject<I> {
        val registry = register.cast().register(name,sup)
        map[registry.id] = registry
        return registry
    }
}