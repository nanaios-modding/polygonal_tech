package com.nanaios.polygonal_tech.lib.register.registry

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

open class BlockRegistryObject<B: Block>(
    protected val block: IRegistryObject<B>,
    protected val item: IRegistryObject<out Item>
): IRegistryObject<B> by block {
    fun getItemRegistryObject(): IRegistryObject<out Item> {
        return item
    }
}