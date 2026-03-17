package com.nanaios.polygonal_tech;

import com.mojang.logging.LogUtils;
import com.nanaios.polygonal_tech.config.PolygonalTechConfig;
import com.nanaios.polygonal_tech.registries.*;
import com.nanaios.polygonal_tech.util.sync.SynchronizeMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PolygonalTech.MODID)
public class PolygonalTech {
    public static final String MODID = "polygonal_tech";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PolygonalTech(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        // コンフィグの登録
        context.registerConfig(ModConfig.Type.COMMON, PolygonalTechConfig.register());

        // アイテムやブロックなどの登録
        PolygonalTechItemRegister.ITEMS.register(bus);
        PolygonalTechBlockRegister.BLOCKS.register(bus);
        PolygonalTechBlockEntityTypeRegister.BLOCK_ENTITIES.register(bus);
        PolygonalTechCreativeModeTabRegister.CREATIVE_TABS.register(bus);
        PolygonalTechMenuTypeRegister.MENU_TYPES.register(bus);

        // commonSetupを登録
        bus.addListener(this::commonSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        // 同期システムの初期化
        SynchronizeMap.scanSynchronizeAnnotation(MODID);
    }

    /// {@link ResourceLocation}を{@link PolygonalTech#MODID}付きで生成する
    /// @param path ResourceLocationのパス
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
