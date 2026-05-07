package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import net.minecraftforge.registries.DeferredRegister

/**
 * 単一の型（例：ブロックのみ、アイテムのみ等）に絞って要素の登録を管理することを目的としたインターフェース。
 * 内部で保持している[DeferredRegister]に対する透過的な操作を提供する。
 *
 * @param T 登録の対象となる基本型
 */
interface IDeferredSingleRegister<T> : IDeferredRegister<T> {
    /**
     * このプロパティは、元の[DeferredRegister]への直接的なアクセスを提供することを目的とする。
     * 本クラスのインスタンスを[DeferredRegister]型として取得する際に使用する。
     */
    val cast: DeferredRegister<T>
}