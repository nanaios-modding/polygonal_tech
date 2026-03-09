package com.nanaios.polygonal_tech.client;

import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechRenderType {
    @SuppressWarnings("removal")
    public static void setRenderTypes() {
        for(RegistryObject<Block> block : PolygonalTechBlockRegister.MACHINE_BLOCKS.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(
                    block.get(),
                    RenderType.cutout()
            );
        }
    }
}
