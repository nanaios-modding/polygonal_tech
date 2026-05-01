package com.nanaios.polygonal_tech.lib.util.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import kotlin.experimental.or

interface IFace {
    val id: ResourceLocation
}

enum class DirectionFace(
    override val id: ResourceLocation,
) : IFace {
    DOWN(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "down")),
    UP(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "up")),
    FRONT(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "north")),
    BACK(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "south")),
    LEFT(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "west")),
    RIGHT(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "east")),
    INTERNAL(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "internal"));

    companion object {
        /**
         * [Enum.ordinal]の値から側面の回転数を取得するマッピング
         * [net.minecraft.core.Direction.NORTH]を0として、反時計回りに1つず増えていく
         * */
        val horizontalId = byteArrayOf(
            -1, -1, 0, 2, 1, 3
        )

        val horizontalFace = arrayOf(
            FRONT,
            RIGHT,
            BACK,
            LEFT
        )

        /**
         * [net.minecraft.core.Direction.NORTH]を前面としたときの、指定された方向の面を返します。
         * */
        fun from(direction: Direction?): DirectionFace {
            return when (direction) {
                Direction.NORTH -> FRONT
                Direction.DOWN -> DOWN
                Direction.UP -> UP
                Direction.SOUTH -> BACK
                Direction.WEST -> LEFT
                Direction.EAST -> RIGHT
                null -> INTERNAL
            }
        }

        /**
         * 既定の前面と現在の前面から、指定された方向の相対的な面を返します
         * @param default - 既定の前面。UP,DOWN以外である必要があります
         * @param current - 現在の前面。UP,DOWN以外である必要があります
         * @param side - 外部からアクセスされている面
         * */
        fun from(default: Direction, current: Direction, side: Direction?): DirectionFace {
            return when (side) {
                null -> INTERNAL
                Direction.UP -> UP
                Direction.DOWN -> DOWN
                else -> {
                    val defaultId = horizontalId[default.ordinal]
                    val currentId = horizontalId[current.ordinal]
                    val sideId = horizontalId[side.ordinal]

                    if ((defaultId or currentId or sideId) < 0) return INTERNAL

                    val rot = (currentId - defaultId) and 3
                    val localIdx = (sideId - rot) and 3

                    horizontalFace[localIdx]
                }
            }
        }
    }
}