package com.nanaios.polygonal_tech.core.util.sync.storage

import com.nanaios.polygonal_tech.core.util.sync.value.ISyncValue

/**
 * [ISyncValue]を保管するためのインターフェース。
 * */
interface ISyncValueStorage {
    fun addValue(value: ISyncValue)
    fun removeValue(value: ISyncValue)
    fun onSyncValueChanged(value: ISyncValue)
}