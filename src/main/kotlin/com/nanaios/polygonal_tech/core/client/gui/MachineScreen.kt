package com.nanaios.polygonal_tech.core.client.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu

open class MachineScreen<M: AbstractContainerMenu>(
    menu:M,
    inventory: Inventory,
    title: Component,
): AbstractContainerScreen<M>(menu, inventory, title) {
    override fun renderBg(guiGraphics: GuiGraphics,partialTick: Float, mouseX: Int, mouseY: Int) {

    }
}