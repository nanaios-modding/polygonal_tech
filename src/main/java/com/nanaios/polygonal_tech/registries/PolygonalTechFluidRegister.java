package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.registration.impl.DeferredFluidRegister;
import com.nanaios.polygonal_tech.registration.impl.MultipleFluidRegister;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechFluidRegister {
    public static final MultipleFluidRegister FLUIDS = new MultipleFluidRegister();
    public static final DeferredFluidRegister BASE_FLUIDS = FLUIDS.create();

    public static final RegistryObject<Fluid> THIRD_FLOW;

    static{
        THIRD_FLOW = BASE_FLUIDS.register(PolygonalTechNamedTokens.THIRD_FLOW);
    }
}

