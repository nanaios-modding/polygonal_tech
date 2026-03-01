package com.nanaios.polygonal_tech.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockRegister {
    public static MultipleRegister<Block> BLOCKS = new MultipleRegister<>(Registries.BLOCK);
    public static DeferredRegister<Block> FACTORY_BLOCKS = BLOCKS.create();

    public static RegistryObject<Block> TEST_FACTOR;

    static {
        TEST_FACTOR = FACTORY_BLOCKS.register("test_factor", () -> new Block(Block.Properties.of()));
    }
}
