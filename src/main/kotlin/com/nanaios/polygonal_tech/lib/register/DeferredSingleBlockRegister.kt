package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

class DeferredSingleBlockRegister(modId: String): DeferredSingleRegister<Block>(
    ForgeRegistries.BLOCKS,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, RegistryObject<out Block>> = mutableMapOf()

        fun getBlockRegistryObject(location: ResourceLocation): RegistryObject<out Block>? {
            return MAP[location]
        }
    }
}