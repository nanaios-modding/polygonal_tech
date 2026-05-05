package com.nanaios.polygonal_tech.core.client.gui

import com.nanaios.polygonal_tech.core.menu.MachineMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

open class MachineScreen<M: MachineMenu>(
    menu:M,
    inventory: Inventory,
    title: Component,
): AbstractContainerScreen<M>(menu, inventory, title) {
    override fun renderBg(guiGraphics: GuiGraphics,partialTick: Float, mouseX: Int, mouseY: Int) {
    }
}