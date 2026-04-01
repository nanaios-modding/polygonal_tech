package com.nanaios.polygonal_tech.lib.sync

fun kotlinTest() {
    var sync = createSyncIntValue(0)
    for(i in 1..10) {
        sync = i
    }
    println("final value = $sync")
}