package com.nanaios.polygonal_tech.lib.sync

open class SyncValueStorage():ISyncValueStorage {
    protected val values: MutableList<ISyncValue<*>> = mutableListOf()

    override fun addValue(value: ISyncValue<*>) {
        values.add(value)
    }

    override fun removeValue(value: ISyncValue<*>) {
        values.remove(value)
    }

    override fun onChanged() {
    }
}