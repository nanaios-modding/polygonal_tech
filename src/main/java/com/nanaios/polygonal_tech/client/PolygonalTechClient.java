package com.nanaios.polygonal_tech.client;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PolygonalTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PolygonalTechClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(PolygonalTechRenderType::setRenderTypes);
    }
}
