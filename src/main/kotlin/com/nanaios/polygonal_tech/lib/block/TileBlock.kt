package com.nanaios.polygonal_tech.lib.block

import com.nanaios.polygonal_tech.lib.register.registry.IRegistryObject
import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleBlockEntityTypeRegister
import com.nanaios.polygonal_tech.lib.tile.ITile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

open class TileBlock(
    protected val id: ResourceLocation,
    properties: Properties
) : BaseEntityBlock(properties) {
    override fun newBlockEntity(
        pos: BlockPos,
        blockState: BlockState
    ): BlockEntity? {
        return getRegistryObject()?.get()?.create(pos, blockState)
    }

    protected fun getRegistryObject(): IRegistryObject<out BlockEntityType<*>>? {
        return DeferredSingleBlockEntityTypeRegister.getBlockRegistryObject(id)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        val registry = getRegistryObject() ?: return null
        return createTickerHelper(type,registry.get()) {level, pos, state, tile ->
            if(tile !is ITile) return@createTickerHelper
            if(level.isClientSide) tile.clientTick(level, pos, state)
            else tile.serverTick(level, pos, state)
        }
    }
}
