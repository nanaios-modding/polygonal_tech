package com.nanaios.polygonal_tech.lib.sync

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SyncPrimitiveValue<T> :ISyncValue<T>, ReadWriteProperty<Any?,T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        println("${property.name} setValue = $value")
    }
}