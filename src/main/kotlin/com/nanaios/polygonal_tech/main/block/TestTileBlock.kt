package com.nanaios.polygonal_tech.main.block

import com.nanaios.polygonal_tech.core.block.TileBlock
import net.minecraft.resources.ResourceLocation

class TestTileBlock(id: ResourceLocation): TileBlock(id,Properties.of()) {
    override fun getDescriptionId(): String {
        return super.getDescriptionId()
    }
}