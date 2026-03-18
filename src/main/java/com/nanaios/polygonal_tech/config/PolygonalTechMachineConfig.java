package com.nanaios.polygonal_tech.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PolygonalTechMachineConfig {
    public static ForgeConfigSpec.LongValue PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME;
    public static ForgeConfigSpec.IntValue PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("machine");
        
        builder.push("photolysis_machine_mk1");

        builder.comment("The time required to process one item in Photolysis Machine Mk1 (in ticks)");
        PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME = builder.defineInRange("process_time", 100L, 1L, Long.MAX_VALUE);

        builder.comment("The minimum light level required to process items in Photolysis Machine Mk1");
        PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL = builder.defineInRange("process_light_level", 12, 0, 15);

        builder.pop();
    }
}
