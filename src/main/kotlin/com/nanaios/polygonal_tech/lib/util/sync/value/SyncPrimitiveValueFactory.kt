package com.nanaios.polygonal_tech.lib.util.sync.value

import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage

fun SyncIntValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncIntValue(storage, SyncType.IN_GUI)
fun SyncIntValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncIntValue(storage, SyncType.ALWAYS)

fun SyncLongValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncLongValue(storage, SyncType.IN_GUI)
fun SyncLongValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncLongValue(storage, SyncType.ALWAYS)

fun SyncFloatValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncFloatValue(storage, SyncType.IN_GUI)
fun SyncFloatValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncFloatValue(storage, SyncType.ALWAYS)

fun SyncDoubleValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncDoubleValue(storage, SyncType.IN_GUI)
fun SyncDoubleValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncDoubleValue(storage, SyncType.ALWAYS)

fun SyncBooleanValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncBooleanValue(storage, SyncType.IN_GUI)
fun SyncBooleanValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncBooleanValue(storage, SyncType.ALWAYS)

fun SyncStringValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncStringValue(storage, SyncType.IN_GUI)
fun SyncStringValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncStringValue(storage, SyncType.ALWAYS)

fun SyncByteValue.Companion.IN_GUI(storage: ISyncValueStorage) = SyncByteValue(storage, SyncType.IN_GUI)
fun SyncByteValue.Companion.ALWAYS(storage: ISyncValueStorage) = SyncByteValue(storage, SyncType.ALWAYS)