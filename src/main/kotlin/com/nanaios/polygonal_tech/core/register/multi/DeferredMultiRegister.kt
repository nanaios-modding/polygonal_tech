package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import net.minecraftforge.eventbus.api.IEventBus

/**
 * [IDeferredMultiRegister]の標準的な実装クラスであり、2つの独立した[IDeferredRegister]のライフサイクルを同期させることを目的としている。
 * 両方のレジストリが同一のMOD IDを持っていることを保証し、イベントバスへの登録処理を1回の呼び出しで済ませる等の利便性を提供する。
 *
 * @param T1 1つ目の登録要素の基底型
 * @param T2 2つ目の登録要素の基底型
 * @property firstRegister 1つ目の要素を管理するレジストリ
 * @property secondRegister 2つ目の要素を管理するレジストリ
 */
abstract class DeferredMultiRegister<T1, T2>(
    open val firstRegister: IDeferredRegister<T1>,
    override val secondRegister: IDeferredRegister<T2>
) : IDeferredMultiRegister<T1, T2>, IDeferredRegister<T1> by firstRegister {
    init {
        if (firstRegister.modId != secondRegister.modId) {
            throw IllegalArgumentException("Both registers must have the same mod ID.")
        }
    }

    /**
     * 保持している2つの[IDeferredRegister]を、同時に[IEventBus]へ接続しアクティブ化することを目的としたメソッド。
     *
     * @param bus 対象となる[IEventBus]
     */
    override fun register(bus: IEventBus) {
        firstRegister.register(bus)
        secondRegister.register(bus)
    }
}