package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.block_entity.PhotolysisMachineMk1;
import com.nanaios.polygonal_tech.registration.impl.MultipleBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registration.impl.DeferredBlockEntityTypeRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockEntityTypeRegister {
    public static MultipleBlockEntityTypeRegister BLOCK_ENTITIES = new MultipleBlockEntityTypeRegister();
    public static DeferredBlockEntityTypeRegister MACHINE_BLOCK_ENTITIES = BLOCK_ENTITIES.create();

    public static RegistryObject<BlockEntityType<PhotolysisMachineMk1>> PHOTOLYSIS_MACHINE_MK1;

    static {
        PHOTOLYSIS_MACHINE_MK1 = MACHINE_BLOCK_ENTITIES.register(
                PolygonalTechNamedTokens.PHOTOLYSIS_MACHINE_MK1,
                PhotolysisMachineMk1::new,
                PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE_MK1
        );
    }
}
