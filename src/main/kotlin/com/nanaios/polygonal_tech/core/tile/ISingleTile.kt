package com.nanaios.polygonal_tech.core.tile

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock

interface ISingleTile: ITile {
    val tileLevel: Level?
    val tilePos: BlockPos
    val defaultFront: Direction

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