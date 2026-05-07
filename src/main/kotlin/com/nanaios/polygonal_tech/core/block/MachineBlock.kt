package com.nanaios.polygonal_tech.core.block

import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty

/**
 * ブロックが向き（[Direction]）の概念を持つ基盤を提供し、
 * 特に工業系MODにおける「正面」や「入出力の方向」の決定を動的に行うことを目的としたベースクラス。
 *
 * @param id 登録用のID
 * @param properties ブロックの特性
 */
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