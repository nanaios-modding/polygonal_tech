package com.nanaios.polygonal_tech.lib.capability

import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue

interface ICapability: ISyncValue {
    fun setStorage(storage: ISyncValueStorage)
}