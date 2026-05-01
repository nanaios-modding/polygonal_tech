package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import net.minecraftforge.eventbus.api.IEventBus

/**
 * 基本的ななIDeferredMultiRegisterの実装クラスです。
 * 2つのIDeferredRegisterを持ちます。
 * 特定のタイプのオブジェクト（例:アイテム、ブロックなど）に特化した登録クラスを作成するためにこのクラスを継承してください。
 * */
abstract class DeferredMultiRegister<T1, T2>(
    val firstRegister: IDeferredRegister<T1>,
    override val secondRegister: IDeferredRegister<T2>
) : IDeferredMultiRegister<T1, T2>, IDeferredRegister<T1> by firstRegister {
    init {
        if (firstRegister.modId != secondRegister.modId) {
            throw IllegalArgumentException("Both registers must have the same mod ID.")
        }
    }

    override fun register(bus: IEventBus) {
        firstRegister.register(bus)
        secondRegister.register(bus)
    }
}