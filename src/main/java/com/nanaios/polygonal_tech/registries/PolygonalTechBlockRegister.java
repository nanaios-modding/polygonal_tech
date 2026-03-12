package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.registration.impl.DeferredBlockRegister;
import com.nanaios.polygonal_tech.registration.impl.MultipleBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockRegister {
    public static MultipleBlockRegister BLOCKS = new MultipleBlockRegister();
    public static DeferredBlockRegister MACHINE_BLOCKS = BLOCKS.create().setBlockItemRegister(
            PolygonalTechItemRegister.BLOCK_ITEMS
    );

    public static RegistryObject<Block> PHOTOLYSIS_MACHINE_MK1;

    static {
            PHOTOLYSIS_MACHINE_MK1 = MACHINE_BLOCKS.registerGuiMachine(PolygonalTechNamedTokens.PHOTOLYSIS_MACHINE_MK1);
    }
}
