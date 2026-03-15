package com.nanaios.polygonal_tech.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PolygonalTechConfig {
    public static ForgeConfigSpec register() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        //init config
        PolygonalTechMachineConfig.init(builder);

        return builder.build();
    }
}
