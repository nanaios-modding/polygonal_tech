package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachine;
import com.nanaios.polygonal_tech.registries.impl.MultipleBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.impl.DeferredBlockEntityTypeRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockEntityTypeRegister {
    public static MultipleBlockEntityTypeRegister BLOCK_ENTITIES = new MultipleBlockEntityTypeRegister();
    public static DeferredBlockEntityTypeRegister MACHINE_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

    public static RegistryObject<BlockEntityType<PhotolysisMachine>> PHOTOLYSIS_MACHINE;

    static {
        PHOTOLYSIS_MACHINE = MACHINE_BLOCK_ENTITIES.register(
                PolygonalTechNamedTokens.PHOTOLYSIS_MACHINE,
                PhotolysisMachine::new,
                PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE
        );
    }
}
