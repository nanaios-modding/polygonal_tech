package com.nanaios.polygonal_tech.lib

import com.nanaios.polygonal_tech.lib.sync.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.sync.SyncValueStorage

open class Test: ISyncValueStorage by SyncValueStorage() {
     fun test() {
         println("test")
     }
}