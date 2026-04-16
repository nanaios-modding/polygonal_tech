package com.nanaios.polygonal_tech.lib.register

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

class DeferredBlockRegister(modId: String): DeferredDoubleRegister<Block, Item>(
    DeferredSingleBlockRegister(modId),
    DeferredSingleItemRegister(modId)
) {
    override fun <I : Block> register(name: String, sup: (ResourceLocation) -> I): IRegistryObject<I> {
        val blockRegistryObject = firstRegister.register(name, sup)
        val itemRegistryObject = secondRegister.register(name) {id ->
            BlockItem(blockRegistryObject.get(), Item.Properties())
        }
        return blockRegistryObject
    }
}