package com.nanaios.polygonal_tech.core.block

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.core.tile.ITile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

/**
 * 空間内に特殊なデータや動作を紐づけるため、[BlockEntity]（タイルエンティティ）を保持するブロックであることを宣言し、
 * タイルの生成とTickの実行（Tickerの発行）を自動化することを目的とする基底クラス。
 *
 * @param id 登録されるブロックのID（タイルと共有で使われることが多いため統一利用される）
 * @param properties ブロックの素材や硬さなどの基本的性質
 */
open class TileBlock(
    protected val id: ResourceLocation,
    properties: Properties
) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        pos: BlockPos,
        blockState: BlockState
    ): BlockEntity? {
        return getRegistryObject()?.get()?.create(pos, blockState)
    }

    protected fun getRegistryObject(): IRegistryObject<out BlockEntityType<*>>? {
        return DeferredSingleTileTypeRegister.getTileTypeRegistryObject(id)
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

    @Suppress("UNCHECKED_CAST")
    protected fun <E : BlockEntity, A : BlockEntity> createTickerHelper(
        type: BlockEntityType<A>,
        otherType: BlockEntityType<E>,
        ticker: BlockEntityTicker<in E>
    ): BlockEntityTicker<A>? {
        return if (otherType === type) ticker as BlockEntityTicker<A> else null
    }
}
