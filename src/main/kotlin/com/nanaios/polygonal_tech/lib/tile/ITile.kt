package com.nanaios.polygonal_tech.lib.tile

import com.nanaios.polygonal_tech.lib.util.face.IFace
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

/**
 * BlockEntityやマルチブロック、パイプネットワークなどの動作するタイルを表すインターフェース。
 * ITileを実装するクラスは以下の責務を負います
 * - serverTick,clientTickによるtickごとの動作の提供
 * - capabilityの保管、管理、提供
 * */
interface ITile: ISyncValueStorage {
    fun serverTick(level: Level, pos: BlockPos, state: BlockState)
    fun clientTick(level: Level, pos: BlockPos, state:BlockState)
    fun <T> getCapability(cap: Capability<T>,face: IFace): LazyOptional<T>
}