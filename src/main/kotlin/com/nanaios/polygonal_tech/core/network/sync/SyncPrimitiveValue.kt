package com.nanaios.polygonal_tech.core.network.sync

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.network.FriendlyByteBuf
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SyncPrimitiveValue<V>(protected var value: V) : ISyncValue, ReadWriteProperty<Any?, V> {
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override var isDirty = false
        protected set

    override fun onSync() {
        isDirty = false
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        if (this.value != value) {
            this.value = value
            isDirty = true
            storage.onSyncValueChanged(this)
        }
    }
}

class SyncIntValue : SyncPrimitiveValue<Int>(0) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readInt()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeInt(value)
    }
}

class SyncLongValue : SyncPrimitiveValue<Long>(0L) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readLong()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeLong(value)
    }
}

class SyncFloatValue: SyncPrimitiveValue<Float>(0f) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readFloat()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeFloat(value)
    }
}

class SyncDoubleValue: SyncPrimitiveValue<Double>(0.0) {
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
) : SyncPrimitiveValue<Boolean>(false) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readBoolean()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeBoolean(value)
    }
}

class SyncStringValue: SyncPrimitiveValue<String>("") {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readUtf()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeUtf(value)
    }
}

class SyncByteValue: SyncPrimitiveValue<Byte>(0) {
    companion object;
    override fun readBuffer(buffer: FriendlyByteBuf) {
        value = buffer.readByte()
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeByte(value.toInt())
    }
}