package com.nanaios.polygonal_tech.core.capability.io

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

enum class IOMode(
    modId: String,
    name: String,
    val canInput:Boolean,
    val canOutput:Boolean,
): IIOMode {
    NONE(PolygonalTech.MOD_ID,"none", false, false),
    INPUT(PolygonalTech.MOD_ID,"input", true, false),
    OUTPUT(PolygonalTech.MOD_ID,"output", false, true),
    INPUT_OUTPUT(PolygonalTech.MOD_ID,"input_output", true, true);

    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(modId, "io_mode/$name")
    override val translation: MutableComponent = Component.translatable("io_mode.${PolygonalTech.MOD_ID}.$name")
}