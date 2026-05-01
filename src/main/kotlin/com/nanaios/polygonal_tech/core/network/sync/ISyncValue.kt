package com.nanaios.polygonal_tech.core.network.sync

import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import net.minecraft.network.FriendlyByteBuf

/**
 * ネットワークで同期する値を表すインターフェース。
 * [com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage]に保管され、ネットワークで同期される値はISyncValueを実装する必要があります。
 * ISyncValueを実装するクラスは以下の責務を負います
 * - writeBuffer,readBufferによるネットワークでの値の読み書き機能の提供
 * */
interface ISyncValue {
    var storage: ISyncValueStorage
    var type: ISyncType
    val isDirty: Boolean

    fun writeBuffer(buffer: FriendlyByteBuf)
    fun readBuffer(buffer: FriendlyByteBuf)
    fun onSync()
}

infix fun <V : ISyncValue> V.bind(storage: ISyncValueStorage): V {
    this.storage = storage
    return this
}

infix fun <V : ISyncValue> V.on(type: ISyncType): V {
    this.type = type
    return this
}