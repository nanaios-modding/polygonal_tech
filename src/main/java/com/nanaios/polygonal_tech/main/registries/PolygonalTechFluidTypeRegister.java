package com.nanaios.polygonal_tech.main.registries;

import com.nanaios.polygonal_tech.main.fluids.ThirdFlowFluidType;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredFluidTypeRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.MultipleFluidTypeRegister;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechFluidTypeRegister {
    public static final MultipleFluidTypeRegister FLUID_TYPES = new MultipleFluidTypeRegister();
    public static final DeferredFluidTypeRegister BASE_FLUID_TYPES = FLUID_TYPES.create();

    public static final RegistryObject<FluidType> THIRD_FLOW_TYPE;

    static{
        THIRD_FLOW_TYPE = BASE_FLUID_TYPES.register(PolygonalTechNamedTokens.THIRD_FLOW, ThirdFlowFluidType::new);
    }
}
