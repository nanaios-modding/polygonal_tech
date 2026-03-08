package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block.base.BaseGuiMachineBlock;
import com.nanaios.polygonal_tech.registries.impl.DeferredBlockRegister;
import com.nanaios.polygonal_tech.registries.impl.MultipleBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockRegister {
    public static MultipleBlockRegister BLOCKS = new MultipleBlockRegister();
    public static DeferredBlockRegister MACHINE_BLOCKS = BLOCKS.create();

    public static RegistryObject<Block> PHOTOLYSIS_MACHINE;

    static {
            PHOTOLYSIS_MACHINE = MACHINE_BLOCKS.register(
                    "photolysis_machine",
                    () -> new BaseGuiMachineBlock<>(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE));
    }
}
