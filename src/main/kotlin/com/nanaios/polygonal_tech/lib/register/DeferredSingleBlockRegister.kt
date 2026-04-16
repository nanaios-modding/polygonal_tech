package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries

class DeferredSingleBlockRegister(modId: String): DeferredSingleRegister<Block>(
    ForgeRegistries.BLOCKS,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out Block>> = mutableMapOf()

        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out Block>? {
            return MAP[location]
        }
    }
}