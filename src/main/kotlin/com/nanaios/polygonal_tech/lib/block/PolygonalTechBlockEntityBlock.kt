package com.nanaios.polygonal_tech.lib.block

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PolygonalTechBlockEntityBlock(
    val id: ResourceLocation,
    properties: Properties
) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        pos: BlockPos,
        blockState: BlockState
    ): BlockEntity? {
        return null;
    }
}
