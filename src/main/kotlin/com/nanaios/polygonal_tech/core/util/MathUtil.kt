package com.nanaios.polygonal_tech.core.util

fun Long.roundInt(): Int {
    return when {
        this >= Int.MAX_VALUE -> Int.MAX_VALUE
        this <= Int.MIN_VALUE -> Int.MIN_VALUE
        else -> this.toInt()
    }
}

fun Long.nonOverflowAdd(other: Long) : Long {
    return when {
        this > 0 && other > 0 && Long.MAX_VALUE - this < other -> Long.MAX_VALUE
        this < 0 && other < 0 && Long.MIN_VALUE - this > other -> Long.MIN_VALUE
        else -> this + other
    }
}