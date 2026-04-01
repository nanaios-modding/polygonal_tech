package com.nanaios.polygonal_tech.lib.sync

/** ISyncValueの保管、管理、同期を行うことを示すインターフェース  */
interface ISyncValueStorage {
    fun addValue(value: ISyncValue<*>)
    fun removeValue(value: ISyncValue<*>)
    fun onChanged()
}
