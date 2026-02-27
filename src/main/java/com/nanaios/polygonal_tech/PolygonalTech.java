package com.nanaios.polygonal_tech;

import com.mojang.logging.LogUtils;
import com.nanaios.polygonal_tech.registries.PolyTechCreativeModeTabRegister;
import com.nanaios.polygonal_tech.registries.PolyTechItemRegister;
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

        PolyTechItemRegister.ITEMS.register(bus);
        PolyTechCreativeModeTabRegister.CREATIVE_TABS.register(bus);
    }
}
