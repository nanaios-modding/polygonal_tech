package com.nanaios.polygonal_tech.lib.tile

import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.lib.register.single.TileType
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

open class TileBlockEntity(
    protected val id: ResourceLocation,
    protected val tileType: TileType<*>,
    protected val pos: BlockPos,
    protected val state: BlockState
): BlockEntity(tileType, pos, state), ITile {
    /**
     * TileTypeが[DeferredSingleTileTypeRegister]もしくはその継承クラスを通して登録されていることを前提としたconstructor。
     * 上記以外のDeferredRegisterを使用している場合は、TileTypeを直接渡すconstructorを使用してください。
     * */
    constructor(id:ResourceLocation, pos:BlockPos, state:BlockState):this(
        id,
        DeferredSingleTileTypeRegister.getTileTypeRegistryObject(id)?.get()!!,
        pos,
        state
    )

    protected val syncValues: MutableList<ISyncValue> = mutableListOf()

    final override fun serverTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onServerTick(level, pos, state)
    }

    final override fun clientTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onClientTick(level, pos, state)
    }

    override fun addValue(value: ISyncValue) {
        syncValues.add(value)
    }

    protected fun getSyncPacket() {

    }

    protected open fun onServerTick(level: Level, pos: BlockPos, state: BlockState) = Unit
    protected open fun onClientTick(level: Level, pos: BlockPos, state: BlockState) = Unit
}