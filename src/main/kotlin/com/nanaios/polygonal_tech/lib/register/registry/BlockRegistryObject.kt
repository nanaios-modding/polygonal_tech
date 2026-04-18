package com.nanaios.polygonal_tech.lib.register.registry

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

open class BlockRegistryObject<I: Block>(
    protected val block: IRegistryObject<I>,
    protected val item: IRegistryObject<out Item>
): IRegistryObject<I> by block {
    fun getItemRegistryObject(): IRegistryObject<out Item> {
        return item
    }
}