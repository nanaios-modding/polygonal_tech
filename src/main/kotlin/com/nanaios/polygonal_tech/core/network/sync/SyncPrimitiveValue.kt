package com.nanaios.polygonal_tech.core.network.sync

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putInt("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getInt("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putLong("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getLong("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putFloat("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getFloat("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putDouble("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getDouble("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putBoolean("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getBoolean("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putString("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getString("value")
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

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putByte("value", value)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        value = nbt.getByte("value")
    }
}