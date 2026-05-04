package com.nanaios.polygonal_tech.core.block

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.MenuProvider
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class GuiMachineBlock(
    id: ResourceLocation,
    properties: Properties
): MachineBlock(id,properties) {
    @Deprecated("Deprecated in Java")
    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider? {
        return null
    }
}