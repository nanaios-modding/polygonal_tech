package com.nanaios.polygonal_tech.lib.tile

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * BlockEntityやマルチブロック、パイプネットワークなどの動作するタイルを表すインターフェース。
 * ITileを実装するクラスは以下の責務を負います
 * - serverTick,clientTickによるtickごとの動作の提供
 *
 * */
interface ITile {
    fun serverTick(level: Level, pos: BlockPos, state: BlockState)
    fun clientTick(level: Level, pos: BlockPos, state:BlockState)
}