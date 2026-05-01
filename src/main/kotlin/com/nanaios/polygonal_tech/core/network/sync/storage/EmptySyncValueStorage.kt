package com.nanaios.polygonal_tech.core.network.sync.storage

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

object EmptySyncValueStorage:ISyncValueStorage {
    override fun addValue(value: ISyncValue) = Unit
    override fun removeValue(value: ISyncValue) = Unit
    override fun onSyncValueChanged(value: ISyncValue) = Unit
}