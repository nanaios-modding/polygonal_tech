package com.nanaios.polygonal_tech.core.network.sync

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.common.util.INBTSerializable

/**
 * ネットワークで同期する値を表すインターフェース。
 * [ISyncValueStorage]に保管され、ネットワークで同期される値はISyncValueを実装する必要があります。
 * ISyncValueを実装するクラスは以下の責務を負います
 * - writeBuffer,readBufferによるネットワークでの値の読み書き機能の提供
 * */
interface ISyncValue: INBTSerializable<CompoundTag> {
    var storage: ISyncValueStorage
    var type: ISyncType
    val isDirty: Boolean
    val isSaving: Boolean
        get() = true

    fun writeBuffer(buffer: FriendlyByteBuf)
    fun readBuffer(buffer: FriendlyByteBuf)
    fun onSync()
}