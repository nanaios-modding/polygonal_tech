package com.nanaios.polygonal_tech.lib.sync

class SyncIntValue(init: Int) : SyncPrimitiveValue<Int>() {
    override var value = init
}

fun createSyncIntValue(init:Int): Int {
    val sync:Int by SyncIntValue(init)
    return sync
}