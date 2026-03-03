package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.TestMachine;
import com.nanaios.polygonal_tech.registries.impl.MultipleBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.impl.DeferredBlockEntityTypeRegister;

public class PolyTechBlockEntityTypeRegister {
    public static MultipleBlockEntityTypeRegister BLOCK_ENTITIES = new MultipleBlockEntityTypeRegister();
    public static DeferredBlockEntityTypeRegister FACTORY_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

    static {
        FACTORY_BLOCK_ENTITIES.register(
                "test_machine",
                TestMachine::new,
                PolyTechBlockRegister.TEST_MACHINE
        );
    }
}
