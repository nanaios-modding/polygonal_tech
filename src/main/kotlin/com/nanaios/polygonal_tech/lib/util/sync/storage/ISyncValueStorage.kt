package com.nanaios.polygonal_tech.lib.util.sync.storage

import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue

/**
 * [ISyncValue]を保管するためのインターフェース。
 * */
interface ISyncValueStorage {
    fun addValue(value: ISyncValue)
    fun onSyncValueChanged(value: ISyncValue)
}