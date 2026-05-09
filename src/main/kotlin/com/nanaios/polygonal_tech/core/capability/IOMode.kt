package com.nanaios.polygonal_tech.core.capability

enum class IOMode(
    val canInput:Boolean,
    val canOutput:Boolean,
) {
    NONE(false, false),
    INPUT(true, false),
    OUTPUT(false, true),
    INPUT_OUTPUT(true, true);
}