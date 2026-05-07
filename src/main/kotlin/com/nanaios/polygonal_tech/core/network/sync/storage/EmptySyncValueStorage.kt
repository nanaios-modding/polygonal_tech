package com.nanaios.polygonal_tech.core.network.sync.storage

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

/**
 * [ISyncValueStorage]のnullオブジェクトパターンを提供する実装。
 * デフォルト状態や、不要な同期管理を省きたいフィールドに対して代入しておく目的で使用する。
 */
object EmptySyncValueStorage:ISyncValueStorage {
    override fun addValue(value: ISyncValue) = Unit
    override fun removeValue(value: ISyncValue) = Unit
    override fun onSyncValueChanged(value: ISyncValue) = Unit
}