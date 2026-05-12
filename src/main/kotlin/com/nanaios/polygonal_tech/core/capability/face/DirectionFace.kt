package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import kotlin.experimental.or

enum class DirectionFace(
    val path: String
) : IFace {
    DOWN( "down"),
    UP("up"),
    FRONT("front"),
    BACK("back"),
    LEFT("left"),
    RIGHT("right"),
    INTERNAL("internal");

    override val translation: Component
        get() = Component.translatable("face.${PolygonalTech.MOD_ID}.$path")

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
         * ブロックの正面が[Direction.NORTH]であると仮定した場合の、指定された絶対方向（[Direction]）に対する論理面（[DirectionFace]）を返すことを目的とするメソッド。
         *
         * @param direction 対象となる絶対方向
         * @return マッピングされた相対論理面。[null]の場合は[INTERNAL]を返す。
         */
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
         * ブロックの初期の正面方向と現在の正面方向から回転量を計算し、
         * 外部からアクセスされた絶対方向（[Direction]）がブロックにとってどの論理面（[DirectionFace]）にあたるのかを算出することを目的としたメソッド。
         *
         * @param default ブロックの既定の正面方向。[Direction.UP], [Direction.DOWN]以外である必要がある。
         * @param current ブロックの現在の正面方向。[Direction.UP], [Direction.DOWN]以外である必要がある。
         * @param side 外部からアクセスされている絶対方向
         * @return 算出された論理面
         */
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