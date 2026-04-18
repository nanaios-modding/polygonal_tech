package com.nanaios.polygonal_tech.lib.util.sync.value

import net.minecraft.network.FriendlyByteBuf

/**
 * ネットワークで同期する値を表すインターフェース。
 * */
interface ISyncValue {
    fun writeBuffer(buffer: FriendlyByteBuf)
    fun readBuffer(buffer: FriendlyByteBuf)
}