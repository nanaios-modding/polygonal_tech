package com.nanaios.polygonal_tech;

import com.mojang.logging.LogUtils;
import com.nanaios.polygonal_tech.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PolygonalTech.MODID)
public class PolygonalTech {

    public static final String MODID = "polygonal_tech";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PolygonalTech(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        PolygonalTechItemRegister.ITEMS.register(bus);
        PolygonalTechBlockRegister.BLOCKS.register(bus);
        PolygonalTechBlockEntityTypeRegister.BLOCK_ENTITIES.register(bus);
        PolygonalTechCreativeModeTabRegister.CREATIVE_TABS.register(bus);
        PolygonalTechMenuTypeRegister.MENU_TYPES.register(bus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
