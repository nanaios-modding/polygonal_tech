package com.nanaios.polygonal_tech.lib.sync

/**
 * ISyncValueの基本的な実装
 */
abstract class SyncValue<T>(protected val storage: ISyncValueStorage,protected var value: T) : ISyncValue<T> {
    protected var isChanged = false

    override fun isChanged(): Boolean {
        return isChanged
    }

    override fun onSyncCompleted() {
        isChanged = false
    }

    override fun cast(): T {
        return value
    }

    override fun getStorage(): ISyncValueStorage {
        return storage
    }
}