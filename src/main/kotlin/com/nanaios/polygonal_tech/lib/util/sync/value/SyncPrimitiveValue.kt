package com.nanaios.polygonal_tech.lib.util.sync.value

import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import net.minecraft.network.FriendlyByteBuf
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SyncPrimitiveValue<V>(
    override val storage: ISyncValueStorage,
    override val syncType: ISyncType,
    protected var value: V
) : ISyncValue, ReadWriteProperty<Any?, V> {
    var isDirty = false
        protected set

    override fun onSync() {
        isDirty = false
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        if (this.value != value) {
            this.value = value
            isDirty = true
        }
    }
}

class SyncIntValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Int>(
    storage,
    syncType,
    0
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readInt()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeInt(value)
    }
}

class SyncLongValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Long>(
    storage,
    syncType,
    0L
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readLong()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeLong(value)
    }
}

class SyncFloatValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Float>(
    storage,
    syncType,
    0f
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readFloat()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeFloat(value)
    }
}

class SyncDoubleValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Double>(
    storage,
    syncType,
    0.0
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readDouble()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeDouble(value)
    }
}

class SyncBooleanValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Boolean>(
    storage,
    syncType,
    false
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readBoolean()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeBoolean(value)
    }
}

class SyncStringValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<String>(
    storage,
    syncType,
    ""
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readUtf()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeUtf(value)
    }
}

class SyncByteValue(
    storage: ISyncValueStorage,
    syncType: ISyncType
) : SyncPrimitiveValue<Byte>(
    storage,
    syncType,
    0
) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readByte()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeByte(value.toInt())
    }
}