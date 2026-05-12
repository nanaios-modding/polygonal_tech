package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.network.chat.Component

enum class IOFace(
    val path: String
) : IFace {
    INPUT_1("input_1"),
    INPUT_2("input_2"),
    INPUT_3("input_3"),
    INPUT_4("input_4"),
    INPUT_5("input_5"),
    INPUT_6("input_6"),
    OUTPUT_1("output_1"),
    OUTPUT_2("output_2"),
    OUTPUT_3("output_3"),
    OUTPUT_4("output_4"),
    OUTPUT_5("output_5"),
    OUTPUT_6("output_6"),
    INPUT_OUTPUT_1("input_output_1"),
    INPUT_OUTPUT_2("input_output_2"),
    INPUT_OUTPUT_3("input_output_3"),
    INPUT_OUTPUT_4("input_output_4"),
    INPUT_OUTPUT_5("input_output_5"),
    INPUT_OUTPUT_6("input_output_6");

    override val translation: Component
        get() = Component.translatable("face.${PolygonalTech.MOD_ID}.$path")
}