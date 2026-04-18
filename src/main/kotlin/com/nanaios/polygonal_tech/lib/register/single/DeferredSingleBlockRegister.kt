package com.nanaios.polygonal_tech.lib.register.single

import com.nanaios.polygonal_tech.lib.register.registry.IRegistryObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

class DeferredSingleBlockRegister(modId: String): DeferredSingleRegister<Block>(
    ForgeRegistries.BLOCKS,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out Block>> = mutableMapOf()

        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out Block>? {
            Collections.unmodifiableMap(MAP)
            return MAP[location]
        }
    }
}