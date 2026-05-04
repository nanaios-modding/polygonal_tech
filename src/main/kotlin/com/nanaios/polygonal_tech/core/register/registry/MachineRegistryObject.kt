package com.nanaios.polygonal_tech.core.register.registry

import com.nanaios.polygonal_tech.core.register.single.TileType
import net.minecraft.world.level.block.Block

open class MachineRegistryObject<T: TileType<*>,B: Block>(
    protected val block: BlockRegistryObject<B>,
    protected val tileType: IRegistryObject<T>
) : IRegistryObject<T> by tileType {
    fun getBlockRegistryObject(): IRegistryObject<B> {
        return block
    }
}