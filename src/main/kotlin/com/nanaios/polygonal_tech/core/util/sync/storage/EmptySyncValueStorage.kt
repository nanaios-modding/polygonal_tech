package com.nanaios.polygonal_tech.core.util.sync.storage

import com.nanaios.polygonal_tech.core.util.sync.value.ISyncValue

object EmptySyncValueStorage:ISyncValueStorage {
    override fun addValue(value: ISyncValue) = Unit
    override fun removeValue(value: ISyncValue) = Unit
    override fun onSyncValueChanged(value: ISyncValue) = Unit
}