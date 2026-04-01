package com.nanaios.polygonal_tech.lib.sync

import net.minecraft.network.FriendlyByteBuf
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SyncPrimitiveValue<T>(
    protected var value: T,
    protected val listener:(T) -> Unit
) : SyncValue<T>(), ReadWriteProperty<Any?,T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(this.value == value) return
        // 値に変更があった場合のみ、値を更新してonChanged()を呼び出す
        this.value = value
        onChanged()
    }

    override fun onChanged() {
        super.onChanged()
        listener(value)
    }
}

open class SyncIntValue(value: Int,listener: (Int) -> Unit = {}) : SyncPrimitiveValue<Int>(value, listener) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeInt(value)
    }

    override fun read(buf: FriendlyByteBuf) {
        value = buf.readInt()
    }
}

open class SyncLongValue(value: Long,listener: (Long) -> Unit = {}) : SyncPrimitiveValue<Long>(value, listener) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeLong(value)
    }
    override fun read(buf: FriendlyByteBuf) {
        value = buf.readLong()
    }
}

open class SyncFloatValue(value: Float,listener: (Float) -> Unit = {}) : SyncPrimitiveValue<Float>(value, listener) {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeFloat(value)
    }
    override fun read(buf: FriendlyByteBuf) {
        value = buf.readFloat()
    }
}