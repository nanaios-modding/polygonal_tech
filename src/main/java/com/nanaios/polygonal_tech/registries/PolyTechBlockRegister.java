package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block.base.BaseEntityBlock;
import com.nanaios.polygonal_tech.registries.impl.DeferredBlockRegister;
import com.nanaios.polygonal_tech.registries.impl.MultipleBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockRegister {
    public static MultipleBlockRegister BLOCKS = new MultipleBlockRegister();
    public static DeferredBlockRegister MACHINE_BLOCKS = BLOCKS.create();

    public static RegistryObject<Block> TEST_MACHINE;

    static {
        TEST_MACHINE = MACHINE_BLOCKS.registerMachine("test_machine", BaseEntityBlock::new);
    }
}
