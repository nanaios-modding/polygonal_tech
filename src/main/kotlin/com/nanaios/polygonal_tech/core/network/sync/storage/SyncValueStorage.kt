package com.nanaios.polygonal_tech.core.network.sync.storage

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

/**
 * 複数の[ISyncValue]（同期フィールド）をまとめて登録・管理し、
 * 内部で値が変更された際（Dirty状態時）に変更イベントを受け取ることを目的とするインターフェース。
 * タイルエンティティ等のコンテナオブジェクトに実装される。
 */
interface ISyncValueStorage {
    fun addValue(value: ISyncValue)
    fun removeValue(value: ISyncValue)
    fun onSyncValueChanged(value: ISyncValue)
}