package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.TestFactory;
import com.nanaios.polygonal_tech.registries.base.BlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.base.DeferredBlockEntityTypeRegister;

public class PolyTechBlockEntityTypeRegister {
    public static BlockEntityTypeRegister BLOCK_ENTITIES = new BlockEntityTypeRegister();
    public static DeferredBlockEntityTypeRegister FACTORY_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

    static {
        FACTORY_BLOCK_ENTITIES.register(
                "test_factor",
                TestFactory::new,
                PolyTechBlockRegister.TEST_FACTORY
        );
    }
}
