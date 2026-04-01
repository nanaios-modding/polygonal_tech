package com.nanaios.polygonal_tech.lib.sync

/**
 * ISyncValueの基本的な実装
 */
abstract class SyncValue<T> : ISyncValue<T> {
    protected var isChanged = false

    override fun isChanged(): Boolean {
        return isChanged
    }

    override fun onSyncCompleted() {
        isChanged = false
    }

    override fun onChanged() {
        isChanged = true
    }
}