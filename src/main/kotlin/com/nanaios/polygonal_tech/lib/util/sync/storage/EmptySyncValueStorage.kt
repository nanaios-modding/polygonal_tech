package com.nanaios.polygonal_tech.lib.util.sync.storage

import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue

object EmptySyncValueStorage:ISyncValueStorage {
    override fun addValue(value: ISyncValue) = Unit
    override fun removeValue(value: ISyncValue) = Unit
    override fun onSyncValueChanged(value: ISyncValue) = Unit
}