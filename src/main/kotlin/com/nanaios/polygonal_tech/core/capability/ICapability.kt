package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue

interface ICapability: ISyncValue {
    val allowInput: Boolean
        get() = true
    val allowOutput: Boolean
        get() = true
}