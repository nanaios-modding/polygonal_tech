package com.nanaios.polygonal_tech.lib.util

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

interface IIOMode {
    val id: ResourceLocation
}

enum class IOMode(
    override val id: ResourceLocation,
    val canInput:Boolean,
    val canOutput:Boolean,
): IIOMode {
    NONE(createIOModeLocation(PolygonalTech.MOD_ID,"none"), false, false),
    INPUT(createIOModeLocation(PolygonalTech.MOD_ID,"input"), true, false),
    OUTPUT(createIOModeLocation(PolygonalTech.MOD_ID,"output"), false, true),
    INPUT_OUTPUT(createIOModeLocation(PolygonalTech.MOD_ID,"input_output"), true, true)
}

fun createIOModeLocation(modId: String,name:String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(modId, "io_mode.$name")
}