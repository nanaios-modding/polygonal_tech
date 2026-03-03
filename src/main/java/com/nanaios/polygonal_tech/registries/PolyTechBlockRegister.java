package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block.base.BaseEntityBlock;
import com.nanaios.polygonal_tech.block_entity.TestFactory;
import com.nanaios.polygonal_tech.registries.base.MultipleRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.registries.impl.DeferredBlockRegister;
import com.nanaios.polygonal_tech.registries.impl.MultipleBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockRegister {
    public static MultipleBlockRegister BLOCKS = new MultipleBlockRegister();
    public static DeferredBlockRegister FACTORY_BLOCKS = BLOCKS.create();

    public static RegistryObject<Block> TEST_FACTORY;

    static {
        TEST_FACTORY = FACTORY_BLOCKS.register("test_factory", () -> new BaseEntityBlock<>(BlockBehaviour.Properties.of(),TEST_FACTORY));
    }
}
