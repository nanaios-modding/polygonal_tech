package com.nanaios.polygonal_tech.lib.util

import net.minecraft.network.FriendlyByteBuf

/**
 * FriendlyByteBufによって読み書きできることを示すインターフェース
 */
interface IFriendlyByteBufSerializer {
    /** FriendlyByteBufから読み取る */
    fun read(buf: FriendlyByteBuf)
    /** FriendlyByteBufへ書き込む */
    fun write(buf: FriendlyByteBuf)
}