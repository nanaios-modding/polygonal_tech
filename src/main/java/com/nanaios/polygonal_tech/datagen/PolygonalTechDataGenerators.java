package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PolygonalTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PolygonalTechDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(event.includeServer(),new PolygonalTechBlockStateProvider(output, event.getExistingFileHelper()));
    }
}