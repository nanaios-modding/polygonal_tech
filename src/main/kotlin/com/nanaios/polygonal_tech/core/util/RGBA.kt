package com.nanaios.polygonal_tech.core.util

data class RGBA(val r: Int, val g: Int, val b: Int, val a: Int) {
    init {
        require(r in 0..255) { "Red value must be between 0 and 255" }
        require(g in 0..255) { "Green value must be between 0 and 255" }
        require(b in 0..255) { "Blue value must be between 0 and 255" }
        require(a in 0..255) { "Alpha value must be between 0 and 255" }
    }

    fun toHex(): String {
        return String.format("#%02X%02X%02X%02X", r, g, b, a)
    }

    companion object {
        fun fromHex(hex: String): RGBA {
            require(hex.startsWith("#") && (hex.length == 7 || hex.length == 9)) { "Hex string must start with '#' and be either 7 or 9 characters long" }
            val r = Integer.parseInt(hex.substring(1, 3), 16)
            val g = Integer.parseInt(hex.substring(3, 5), 16)
            val b = Integer.parseInt(hex.substring(5, 7), 16)
            val a = if (hex.length == 9) Integer.parseInt(hex.substring(7, 9), 16) else 255
            return RGBA(r, g, b, a)
        }
    }
}
