package com.nanaios.polygonal_tech.lib.sync

/**
 * ISyncValueの保管、管理、同期を行うことを示すインターフェース 
 */
interface ISyncValueStorage {
    /** ISyncValueを追加する */
    fun addValue(value: ISyncValue<*>)
    /** ISyncValueを削除する */
    fun removeValue(value: ISyncValue<*>)
    /** ISyncValueが変更されたときに呼び出すべきメソッド */
    fun onSyncValueChanged(value:ISyncValue<*>)
}
