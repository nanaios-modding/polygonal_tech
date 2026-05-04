package com.nanaios.polygonal_tech.core.block

import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty

open class MachineBlock(
    id: ResourceLocation,
    properties: Properties
) : TileBlock(id, properties) {
    companion object {
        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
    }

    init {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState?>) {
        builder.add(FACING)
    }
}