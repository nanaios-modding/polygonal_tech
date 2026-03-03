package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.TestFactor;
import com.nanaios.polygonal_tech.registries.base.BlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.base.DeferredBlockEntityTypeRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockEntityTypeRegister {
    public static BlockEntityTypeRegister BLOCK_ENTITIES = new BlockEntityTypeRegister();
    public static DeferredBlockEntityTypeRegister FACTORY_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

    public static RegistryObject<BlockEntityType<TestFactor>> TEST_FACTOR;

    static {
        TEST_FACTOR = FACTORY_BLOCK_ENTITIES.register(
                "test_factor",
                () -> BlockEntityType.Builder.of(
                        TestFactor::new,
                        PolyTechBlockRegister.TEST_FACTOR.get()
                ).build(null)
        );
    }
}
