package com.nanaios.polygonal_tech.core.tile

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock

/**
 * 単独で機能を持つブロックエンティティ（タイルエンティティ）が必要とする基本的な位置情報や、向き情報を一元的に提供することを目的としたインターフェース。
 * 実実装クラス（[SingleTile]など）において、共通のプロパティアクセスを保証するために使用される。
 */
interface ISingleTile: ITile {
    /**
     * 現在タイルが存在する[Level]（ワールド）へのアクセスを提供することを目的とする。
     */
    val tileLevel: Level?
    /**
     * タイルが配置されているワールド内の座標（[BlockPos]）を返すことを目的とする。
     */
    val tilePos: BlockPos
    /**
     * ブロックが持つデフォルトの正面方向（通常は[Direction.NORTH]など）を定義することを目的とする。
     */
    val defaultFront: Direction

    /**
     * ブロックの現在のステート（[BlockState]）から動的に正面方向を計算し、取得することを目的とするプロパティ。
     * [HorizontalDirectionalBlock.FACING]プロパティが存在する場合はその方向を返し、なければ[defaultFront]を返す。
     */
    val currentFront: Direction
        get(){
        val level = tileLevel ?: return defaultFront
        val state = level.getBlockState(tilePos)

        return if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
            state.getValue(HorizontalDirectionalBlock.FACING)
        } else {
            defaultFront
        }
    }
}