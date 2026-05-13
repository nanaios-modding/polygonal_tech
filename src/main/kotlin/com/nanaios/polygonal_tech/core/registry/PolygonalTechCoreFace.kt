package com.nanaios.polygonal_tech.core.registry

import com.nanaios.polygonal_tech.core.PolygonalTechCore
import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.face.EmptyFace
import com.nanaios.polygonal_tech.core.capability.face.IOFace
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleFaceRegister

object PolygonalTechCoreFace {
    val FACES = DeferredSingleFaceRegister(PolygonalTechCore.MOD_ID)

    val EMPTY = FACES.register("empty") { EmptyFace }

    val DOWN = FACES.register("down") { DirectionFace.DOWN }
    val UP = FACES.register("up") { DirectionFace.UP }
    val LEFT = FACES.register("left") { DirectionFace.LEFT }
    val RIGHT = FACES.register("right") { DirectionFace.RIGHT }
    val FRONT  = FACES.register("front") { DirectionFace.FRONT }
    val BACK = FACES.register("back") { DirectionFace.BACK }
    val INTERNAL = FACES.register("internal") { DirectionFace.INTERNAL }

    val INPUT_1 = FACES.register("input_1") { IOFace.INPUT_1 }
    val INPUT_2 = FACES.register("input_2") { IOFace.INPUT_2 }
    val INPUT_3 = FACES.register("input_3") { IOFace.INPUT_3 }
    val INPUT_4 = FACES.register("input_4") { IOFace.INPUT_4 }
    val INPUT_5 = FACES.register("input_5") { IOFace.INPUT_5 }
    val INPUT_6 = FACES.register("input_6") { IOFace.INPUT_6 }
    val OUTPUT_1 = FACES.register("output_1") { IOFace.OUTPUT_1 }
    val OUTPUT_2 = FACES.register("output_2") { IOFace.OUTPUT_2 }
    val OUTPUT_3 = FACES.register("output_3") { IOFace.OUTPUT_3 }
    val OUTPUT_4 = FACES.register("output_4") { IOFace.OUTPUT_4 }
    val OUTPUT_5 = FACES.register("output_5") { IOFace.OUTPUT_5 }
    val OUTPUT_6 = FACES.register("output_6") { IOFace.OUTPUT_6 }
    val INPUT_OUTPUT_1 = FACES.register("input_output_1") { IOFace.INPUT_OUTPUT_1 }
    val INPUT_OUTPUT_2 = FACES.register("input_output_2") { IOFace.INPUT_OUTPUT_2 }
    val INPUT_OUTPUT_3 = FACES.register("input_output_3") { IOFace.INPUT_OUTPUT_3 }
    val INPUT_OUTPUT_4 = FACES.register("input_output_4") { IOFace.INPUT_OUTPUT_4 }
    val INPUT_OUTPUT_5 = FACES.register("input_output_5") { IOFace.INPUT_OUTPUT_5 }
    val INPUT_OUTPUT_6 = FACES.register("input_output_6") { IOFace.INPUT_OUTPUT_6 }
}