package com.nanaios.polygonal_tech.main.registries;

import com.nanaios.polygonal_tech.main.block.pipe.FluidPipeBlock;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredBlockRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.MultipleBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockRegister {
    public static MultipleBlockRegister BLOCKS = new MultipleBlockRegister();
    public static DeferredBlockRegister MACHINE_BLOCKS = BLOCKS.create().setBlockItemRegister(
            PolygonalTechItemRegister.BLOCK_ITEMS
    );
    public static DeferredBlockRegister PIPE_BLOCKS = BLOCKS.create().setBlockItemRegister(
            PolygonalTechItemRegister.BLOCK_ITEMS
    );

    public static RegistryObject<Block> PHOTOLYSIS_MACHINE_MK1;
    public static RegistryObject<Block> FLUID_PIPE;

    static {
            PHOTOLYSIS_MACHINE_MK1 = MACHINE_BLOCKS.registerGuiMachine(PolygonalTechNamedTokens.PHOTOLYSIS_MACHINE_MK1, () -> PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE_MK1);
            FLUID_PIPE = PIPE_BLOCKS.registerPipe(PolygonalTechNamedTokens.FLUID_PIPE, () -> PolygonalTechBlockEntityTypeRegister.FLUID_PIPE,FluidPipeBlock::new);
    }
}
