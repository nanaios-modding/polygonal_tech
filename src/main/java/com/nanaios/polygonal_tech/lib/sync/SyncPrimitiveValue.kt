package com.nanaios.polygonal_tech.lib.sync

import net.minecraft.network.FriendlyByteBuf
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * IntやFloatなどの基本的な値を同期するクラス
 */
abstract class SyncPrimitiveValue<T>(
    storage: ISyncValueStorage,
    value:T
) : SyncValue<T>(storage,value), ReadWriteProperty<Any?,T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(this.value == value) return
        this.value = value

        storage.onSyncValueChanged(this)
    }
}

open class SyncIntValue(value: Int,storage: ISyncValueStorage) : SyncPrimitiveValue<Int>(storage,value) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeInt(value)
    }

    override fun read(buf: FriendlyByteBuf) {
        value = buf.readInt()
    }
}

open class SyncLongValue(value: Long,storage: ISyncValueStorage) : SyncPrimitiveValue<Long>(storage,value) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeLong(value)
    }
    override fun read(buf: FriendlyByteBuf) {
        value = buf.readLong()
    }
}

open class SyncFloatValue(value: Float,storage: ISyncValueStorage) : SyncPrimitiveValue<Float>(storage,value) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeFloat(value)
    }
    override fun read(buf: FriendlyByteBuf) {
        value = buf.readFloat()
    }
}