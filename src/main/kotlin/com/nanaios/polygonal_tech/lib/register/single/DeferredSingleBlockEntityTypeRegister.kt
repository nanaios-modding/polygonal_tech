package com.nanaios.polygonal_tech.lib.register.single

import com.nanaios.polygonal_tech.lib.register.registry.IRegistryObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.ForgeRegistries

class DeferredSingleBlockEntityTypeRegister(modId: String): DeferredSingleRegister<BlockEntityType<*>>(
    ForgeRegistries.BLOCK_ENTITY_TYPES,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out BlockEntityType<*>>> = mutableMapOf()

        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out BlockEntityType<*>>? {
            return MAP[location]
        }
    }
}