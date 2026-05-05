package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.menu.MachineMenu
import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleMenuTypeRegister
import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.level.block.state.BlockState

open class SingleGuiMachineTile(
    id: ResourceLocation,
    pos: BlockPos,
    state: BlockState
): SingleMachineTile(id,pos,state), MenuProvider {
    override fun getDisplayName(): Component = Component.translatable(Util.makeDescriptionId("block",id))

    override fun createMenu(
        windowId: Int,
        inventory: Inventory,
        player: Player
    ): AbstractContainerMenu? {
        val menuType = DeferredSingleMenuTypeRegister.getMenuTypeRegistryObject(id)
        return if(menuType != null) MachineMenu(id,menuType.get(),windowId,inventory,pos) else null
    }
}