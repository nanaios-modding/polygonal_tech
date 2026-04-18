package com.nanaios.polygonal_tech.lib.register.multi

import com.nanaios.polygonal_tech.lib.register.registry.BlockRegistryObject
import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleBlockRegister
import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleItemRegister
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

class DeferredBlockRegister(modId: String): DeferredMultiRegister<Block, Item>(
    DeferredSingleBlockRegister(modId),
    DeferredSingleItemRegister(modId)
) {
    override fun <I : Block> register(name: String, sup: (ResourceLocation) -> I): BlockRegistryObject<I> {
        val blockRegistryObject = firstRegister.register(name, sup)
        val itemRegistryObject = secondRegister.register(name) {id ->
            BlockItem(blockRegistryObject.get(), Item.Properties())
        }
        return BlockRegistryObject(blockRegistryObject, itemRegistryObject)
    }
}