package com.nanaios.polygonal_tech.main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PolygonalTechMachineConfig {
    public static ForgeConfigSpec.LongValue PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME;
    public static ForgeConfigSpec.IntValue PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL;
    public static ForgeConfigSpec.LongValue PHOTOLYSIS_MACHINE_MK1_PRODUCED_AMOUNT;
    public static ForgeConfigSpec.LongValue PHOTOLYSIS_MACHINE_MK1_OUTPUT_TANK_CAPACITY;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("machine");

        builder.push("photolysis_machine_mk1");
        builder.comment("The time required to process one item in Photolysis Machine Mk1 (in ticks)");
        PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME = builder.defineInRange("process_time", 100L, 1L, Long.MAX_VALUE);
        builder.comment("The minimum light level required to process items in Photolysis Machine Mk1");
        PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL = builder.defineInRange("process_light_level", 12, 0, 15);
        builder.comment("The amount of fluid produced by Photolysis Machine Mk1 per item (in milli buckets)");
        PHOTOLYSIS_MACHINE_MK1_PRODUCED_AMOUNT = builder.defineInRange("output_amount", 1000L, 1L, Long.MAX_VALUE);
        builder.comment("The capacity of the output tank in Photolysis Machine Mk1 (in milli buckets)");
        PHOTOLYSIS_MACHINE_MK1_OUTPUT_TANK_CAPACITY = builder.defineInRange("output_tank_capacity", 10000L, 1L, Long.MAX_VALUE);
        builder.pop();

        builder.pop();
    }
}
