package com.nanaios.polygonal_tech.lib.sync

import com.nanaios.polygonal_tech.lib.util.IFriendlyByteBufSerializer

/**
 * ネットワークによって同期される値を表すインターフェース。
 */
interface ISyncValue<T>: IFriendlyByteBufSerializer {
    /** 値が変更されているかどうかを返す */
    fun isChanged(): Boolean
    /** 値の同期が完了した際に呼び出すべきメソッド */
    fun onSyncCompleted()
    /** 値が変更されたときに呼び出すべきメソッド */
    fun onChanged()
}