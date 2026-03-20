package com.nanaios.polygonal_tech.client;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PolygonalTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PolygonalTechClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(PolygonalTechRenderType::setRenderTypes);
        event.enqueueWork(PolygonalTechScreen::registerScreens);
    }
}
