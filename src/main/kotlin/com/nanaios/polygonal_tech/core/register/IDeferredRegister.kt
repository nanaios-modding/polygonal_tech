package com.nanaios.polygonal_tech.core.register

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import com.nanaios.polygonal_tech.core.util.IModIdProvider
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject

/**
 * [DeferredRegister]をラップし、移譲（Delegate）による継承を容易にすることを目的としたinterface。
 * [DeferredRegister]にはpublicなコンストラクタが存在せず、通常の継承が難しいため、
 * 本interfaceをMixinとして実装することで、少ない記述量で拡張・管理を可能にする。
 *
 * @param T 登録対象となる要素の型 (例: Block, Itemなど)
 */
interface IDeferredRegister<T>: IModIdProvider {
    /**
     * 登録済みのエントリ一覧を取得するためのプロパティ。[DeferredRegister.getEntries]のラップ。
     */
    val entries: MutableCollection<RegistryObject<T>>
    /**
     * レジストリのキーを取得するためのプロパティ。[DeferredRegister.getRegistryKey]のラップ。
     */
    val registryKey: ResourceKey<out Registry<T>>
    /**
     * レジストリの名前（識別子）を取得するためのプロパティ。[DeferredRegister.getRegistryName]のラップ。
     */
    val registryName: ResourceLocation

    /**
     * 新たな要素を登録するためのメソッド。[DeferredRegister.register]のラップ。
     *
     * @param I 実際に登録する要素の派生型
     * @param name 要素の登録名 (IDのパス部分)
     * @param sup 要素を生成して返す関数。引数として割り当てられる[ResourceLocation]を受け取る。
     * @return 登録された要素を内包する[IRegistryObject]
     */
    fun <I : T> register(name: String, sup: (location:ResourceLocation) -> I): IRegistryObject<I>
    /**
     * このレジストリをイベントバスに接続して実際に登録処理を実行させるためのメソッド。[DeferredRegister.register]のラップ。
     *
     * @param bus 登録処理をフックする対象となるForgeの[IEventBus]
     */
    fun register(bus: IEventBus)
}

/**
 * Mixinによって拡張された[DeferredRegister]を、[IDeferredRegister]として扱うために安全なキャストを行う拡張関数。
 * 毎回のキャスト処理をインライン化してオーバーヘッドを無くすことを目的としている。
 *
 * @param T 登録対象の型
 * @return [IDeferredRegister]にキャストされた同インスタンス
 */
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <T>DeferredRegister<T>.cast():IDeferredRegister<T> {
    // mixinによってDeferredRegisterはIDeferredRegisterを実装するため、キャストして返すことができます。
    return this as IDeferredRegister<T>
}
