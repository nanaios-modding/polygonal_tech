package com.nanaios.polygonal_tech.lib.tile

import com.nanaios.polygonal_tech.lib.util.IFace
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional

interface ISingleTile: ITile, ICapabilityProvider {
    val tileLevel: Level?
    val tilePos: BlockPos
    val defaultFront: Direction

    fun getCurrentFront(): Direction {
        val level = tileLevel ?: return defaultFront
        val state = level.getBlockState(tilePos)
        return if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
            state.getValue(HorizontalDirectionalBlock.FACING)
        } else {
            defaultFront
        }
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return LazyOptional.empty()
    }
}