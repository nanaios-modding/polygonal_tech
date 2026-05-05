package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.block.GuiMachineBlock
import com.nanaios.polygonal_tech.core.block.MachineBlock
import com.nanaios.polygonal_tech.core.register.registry.BlockRegistryObject
import com.nanaios.polygonal_tech.core.register.registry.MachineRegistryObject
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.core.register.single.Tile
import com.nanaios.polygonal_tech.core.register.single.TileType
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

class DeferredMachineRegister(modId: String): DeferredMultiRegister<TileType<*>, Block>(
    DeferredSingleTileTypeRegister(modId),
    DeferredBlockRegister(modId)
) {
    fun <I : Tile> registerMachine(
        name: String,
        sup: (ResourceLocation, BlockPos, BlockState) -> I
    ): MachineRegistryObject<TileType<I>, MachineBlock> {
        val blockRegistryObject = secondRegister.register(name) { location ->
            MachineBlock(location, BlockBehaviour.Properties.of())
        } as BlockRegistryObject<MachineBlock>

        val tileTypeRegistryObject =
            (firstRegister as DeferredSingleTileTypeRegister).register(name, blockRegistryObject, sup)
        return MachineRegistryObject(blockRegistryObject, tileTypeRegistryObject)
    }

    fun <I : Tile> registerGuiMachine(
        name: String,
        sup: (ResourceLocation, BlockPos, BlockState) -> I
    ): MachineRegistryObject<TileType<I>, GuiMachineBlock> {
        val blockRegistryObject = secondRegister.register(name) { location ->
            GuiMachineBlock(location, BlockBehaviour.Properties.of())
        } as BlockRegistryObject<GuiMachineBlock>

        val tileTypeRegistryObject =
            (firstRegister as DeferredSingleTileTypeRegister).register(name, blockRegistryObject, sup)
        return MachineRegistryObject(blockRegistryObject, tileTypeRegistryObject)
    }
}