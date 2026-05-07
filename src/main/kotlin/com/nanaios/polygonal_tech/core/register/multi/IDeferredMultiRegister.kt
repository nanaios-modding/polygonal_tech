package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.register.IDeferredRegister

/**
 * 2種類の異なる[IDeferredRegister]をペアとしてまとめ、同時に管理・登録処理を行うことを目的とするインターフェース。
 * ブロックとそのブロックアイテムなど、常にセットで登録されるべき要素を一つのレジストリで扱うために使用される。
 *
 * @param T1 1つ目の登録要素の基本型（例：Block）
 * @param T2 2つ目の登録要素の基本型（例：Item）
 */
interface IDeferredMultiRegister<T1,T2>: IDeferredRegister<T1> {
    /**
     * 2つ目の登録要素を管理する[IDeferredRegister]へのアクセスを提供するためのプロパティ。
     */
    val secondRegister: IDeferredRegister<T2>
}