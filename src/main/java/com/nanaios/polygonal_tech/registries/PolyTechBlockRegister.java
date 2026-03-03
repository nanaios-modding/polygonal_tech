package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block.base.BaseEntityBlock;
import com.nanaios.polygonal_tech.block_entity.TestFactory;
import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockRegister {
    public static MultipleRegister<Block> BLOCKS = new MultipleRegister<>(ForgeRegistries.BLOCKS);
    public static WrapperDeferredRegister<Block> FACTORY_BLOCKS = BLOCKS.create();

    public static RegistryObject<Block> TEST_FACTORY;

    static {
        TEST_FACTORY = FACTORY_BLOCKS.register("test_factor", () -> new BaseEntityBlock<>(BlockBehaviour.Properties.of(), TestFactory::new));
    }
}
