package com.nanaios.polygonal_tech.lib.util.sync.value

import kotlin.properties.ReadWriteProperty

interface ISyncPrimitiveValue<V>:ISyncValue, ReadWriteProperty<Any?, V>