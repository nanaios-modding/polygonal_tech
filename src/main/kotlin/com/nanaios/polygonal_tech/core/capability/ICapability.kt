package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

/**
 * 各種Capability（エネルギー、流体、アイテム等）をネットワーク同期可能な[ISyncValue]として扱い、
 * 同時に入出力の制御フラグを持たせることを目的としたベースインターフェース。
 */
interface ICapability: ISyncValue {
    /**
     * 外部からこのCapabilityに対してデータ（エネルギーやアイテムなど）を搬入できるかを示す。
     * デフォルトで[true]を返す。
     */
    val allowInput: Boolean
        get() = true

    /**
     * 外部からこのCapabilityを介してデータ（エネルギーやアイテムなど）を搬出できるかを示す。
     * デフォルトで[true]を返す。
     */
    val allowOutput: Boolean
        get() = true
}