package com.nanaios.polygonal_tech.core.network.sync.storage

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

/**
 * [ISyncValue]を保管するためのインターフェース。
 * */
interface ISyncValueStorage {
    fun addValue(value: ISyncValue)
    fun removeValue(value: ISyncValue)
    fun onSyncValueChanged(value: ISyncValue)
}