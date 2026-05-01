package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.ForgeRegistries

typealias TileType<T> = BlockEntityType<T>
typealias Tile = BlockEntity

class DeferredSingleTileTypeRegister(modId: String) : DeferredSingleRegister<TileType<*>>(
    ForgeRegistries.BLOCK_ENTITY_TYPES, modId, MAP
) {
    companion object {
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out TileType<*>>> = mutableMapOf()

        fun getTileTypeRegistryObject(location: ResourceLocation): IRegistryObject<out TileType<*>>? {
            return MAP[location]
        }
    }

    fun <I : Tile> register(
        name: String,
        block: IRegistryObject<out Block>,
        sup: (ResourceLocation, BlockPos, BlockState) -> I
    ): IRegistryObject<TileType<I>> {
        return super.register(block.id.path) {
            BlockEntityType.Builder.of(
                { pos, state -> sup(block.id, pos, state) },
                block.get()
            ).build(null)
        }
    }

    fun <I : Tile> register(block: IRegistryObject<out Block>, sup: (ResourceLocation, BlockPos, BlockState) -> I) =
        register(
            block.id.path,
            block,
            sup
        )
}