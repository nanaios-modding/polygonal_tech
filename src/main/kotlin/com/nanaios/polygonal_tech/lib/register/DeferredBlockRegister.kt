package com.nanaios.polygonal_tech.lib.register

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

class DeferredBlockRegister(modId: String): DeferredDoubleRegister<Block, Item>(
    DeferredSingleBlockRegister(modId),
    DeferredSingleItemRegister(modId)
) {
    override fun <I : Block> register(name: String, sup: Supplier<out I>): RegistryObject<I> {
        val blockRegistryObject: RegistryObject<I> = firstRegister.register(name, sup)
        val itemRegistryObject: RegistryObject<Item> = secondRegister.register(name) {
            BlockItem(blockRegistryObject.get(), Item.Properties())
        }
        return blockRegistryObject
    }
}