package com.nanaios.polygonal_tech.lib.util

inline fun Long.roundInt(): Int {
    return when {
        this >= Int.MAX_VALUE -> Int.MAX_VALUE
        this <= Int.MIN_VALUE -> Int.MIN_VALUE
        else -> this.toInt()
    }
}