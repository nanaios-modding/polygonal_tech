package com.nanaios.polygonal_tech.lib.util.sync.value

import net.minecraft.network.FriendlyByteBuf
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage

/**
 * ネットワークで同期する値を表すインターフェース。
 * [ISyncValueStorage]に保管され、ネットワークで同期される値はISyncValueを実装する必要があります。
 * ISyncValueを実装するクラスは以下の責務を負います
 * - writeBuffer,readBufferによるネットワークでの値の読み書き機能の提供
 * */
interface ISyncValue {
    fun writeBuffer(buffer: FriendlyByteBuf)
    fun readBuffer(buffer: FriendlyByteBuf)
    fun getStorage():ISyncValue
    fun getSyncType(): ISyncType
    fun onSync()
}