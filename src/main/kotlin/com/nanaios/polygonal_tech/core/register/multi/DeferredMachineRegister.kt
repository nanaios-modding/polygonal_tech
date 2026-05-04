package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.block.GuiMachineBlock
import com.nanaios.polygonal_tech.core.block.MachineBlock
import com.nanaios.polygonal_tech.core.register.registry.BlockRegistryObject
import com.nanaios.polygonal_tech.core.register.registry.MachineRegistryObject
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.core.register.single.TileType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

class DeferredMachineRegister(modId: String): DeferredMultiRegister<TileType<*>, Block>(
    DeferredSingleTileTypeRegister(modId),
    DeferredBlockRegister(modId)
) {
    fun <I : TileType<*>> registerMachine(name: String, sup: (location: ResourceLocation) -> I): MachineRegistryObject<I, MachineBlock> {
        val tileTypeRegistryObject = firstRegister.register(name, sup)
        val blockRegistryObject = secondRegister.register(name) {
            MachineBlock(tileTypeRegistryObject.id, BlockBehaviour.Properties.of())
        } as BlockRegistryObject<MachineBlock>
        return MachineRegistryObject(blockRegistryObject, tileTypeRegistryObject)
    }

    fun <I : TileType<*>> registerGuiMachine(name: String, sup: (location: ResourceLocation) -> I): MachineRegistryObject<I, GuiMachineBlock> {
        val tileTypeRegistryObject = firstRegister.register(name, sup)
        val blockRegistryObject = secondRegister.register(name) {
            GuiMachineBlock(tileTypeRegistryObject.id, BlockBehaviour.Properties.of())
        } as BlockRegistryObject<GuiMachineBlock>
        return MachineRegistryObject(blockRegistryObject, tileTypeRegistryObject)
    }
}