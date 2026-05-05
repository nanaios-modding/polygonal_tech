package com.nanaios.polygonal_tech.main.client.gui

import com.nanaios.polygonal_tech.core.client.gui.MachineScreen
import com.nanaios.polygonal_tech.core.menu.MachineMenu
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class TestMachineScreen(
    menu: MachineMenu,
    playerInv: Inventory,
    title: Component,
): MachineScreen<MachineMenu>(menu, playerInv, title) {
    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY)
    }
}