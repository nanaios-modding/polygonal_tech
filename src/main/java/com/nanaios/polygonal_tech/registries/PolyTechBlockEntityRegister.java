package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.TestFactor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PolyTechBlockEntityRegister {
    public static MultipleRegister<BlockEntityType<?>> BLOCK_ENTITIES = new MultipleRegister<>(Registries.BLOCK_ENTITY_TYPE);
    public static DeferredRegister<BlockEntityType<?>> FACTORY_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

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
