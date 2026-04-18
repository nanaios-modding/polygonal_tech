package com.nanaios.polygonal_tech.lib.register.single

import com.nanaios.polygonal_tech.lib.register.registry.IRegistryObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.ForgeRegistries

typealias TileType<T> = BlockEntityType<T>

class DeferredSingleTileTypeRegister(modId: String): DeferredSingleRegister<TileType<*>>(
    ForgeRegistries.BLOCK_ENTITY_TYPES,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out TileType<*>>> = mutableMapOf()

        fun getTileTypeRegistryObject(location: ResourceLocation): IRegistryObject<out TileType<*>>? {
            return MAP[location]
        }
    }

    fun <I : TileType<*>> register(block: IRegistryObject<out Block>, sup: (ResourceLocation) -> I): IRegistryObject<I> {
        return super.register(block.id.path, sup)
    }
}